/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.cloud.pi.admin.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.http.Method;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.admin.pojo.dto.UserEditDTO;
import me.cloud.pi.admin.pojo.dto.UserImportDTO;
import me.cloud.pi.admin.pojo.form.UserEditForm;
import me.cloud.pi.admin.pojo.form.UserForm;
import me.cloud.pi.admin.pojo.query.UserQueryParam;
import me.cloud.pi.admin.pojo.vo.OptionalUserVO;
import me.cloud.pi.admin.pojo.vo.UserInfoVO;
import me.cloud.pi.admin.pojo.vo.UserVO;
import me.cloud.pi.admin.service.UserService;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.mybatis.base.BaseQueryParam;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UserController", description = "用户管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('sys_user_query')")
    @Operation(summary = "用户查询")
    public ResponseData<PiPage<UserVO>> getUsers(UserQueryParam userQuery) {
        return ResponseData.ok(userService.getUsers(userQuery));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys_user_add')")
    @Operation(summary = "新增用户")
    public ResponseData<?> save(@RequestBody @Valid UserForm form) {
        userService.save(form);
        return ResponseData.ok();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys_user_edit')")
    @Operation(summary = "编辑用户")
    public ResponseData<?> edit(@RequestBody @Valid UserEditForm userEditForm) {
        userService.edit(userEditForm);
        return ResponseData.ok();
    }

    @PreAuthorize("hasAuthority('sys_user_delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除")
    public ResponseData<?> delete(@PathVariable String ids) {
        userService.delete(ids);
        return ResponseData.ok();
    }

    @GetMapping("/export")
    @SneakyThrows
    @Operation(summary = "导出用户列表")
    public void export(UserQueryParam query, HttpServletResponse response) {
        userService.export(query, response);
    }

    @GetMapping("/userImportTemp")
    @SneakyThrows
    @Operation(summary = "用户导入模板")
    public void userImportTemp(HttpServletResponse response) {
        userService.downloadUserImportTemp(response);
    }

    @PostMapping("/import")
    @Operation(summary = "导入用户")
    public ResponseData<?> importUser(UserImportDTO dto) {
        return ResponseData.ok(userService.importUser(dto));
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    public ResponseData<UserInfoVO> info() {
        return ResponseData.ok(userService.getUserInfo());
    }

    @PutMapping("/profileEdit")
    @Operation(summary = "修改个人信息")
    public ResponseData<?> editPersonalInfo(@RequestBody UserEditDTO userEditDTO) {
        userService.editPersonalInfo(userEditDTO);
        return ResponseData.ok();
    }

    @GetMapping("/passReset")
    @Operation(summary = "密码重置")
    public ResponseData<?> resetPass(Long id) {
        userService.resetPass(id);
        return ResponseData.ok();
    }

    @GetMapping("/optionalUsers")
    @Operation(summary = "可选用户。如：为角色指定用户时，需要先查询出可以选择的用户列表，然后选择用户")
    public ResponseData<PiPage<OptionalUserVO>> getOptionalUsers(BaseQueryParam query) {
        return ResponseData.ok(userService.getOptionalUsers(query));
    }

    @SneakyThrows
    @PostMapping("/uploadAvatar")
    @Operation(summary = "上传头像")
    public ResponseData<?> uploadAvatar(MultipartFile file, String username, String avatar) {
        // Create a minioClient with the MinIO server, its access key and secret key.
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://pi:9000")
                        .credentials("minio", "minio123")
                        .build();

        // Make 'admin' bucket if not exist.
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("pi-cloud").build());
        if (!found) {
            // Make a new bucket called 'admin'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("pi-cloud").build());

            String policyJson = "{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": \"*\",\n" +
                    "            \"Action\": [\n" +
                    "                \"s3:GetObject\"\n" +
                    "            ],\n" +
                    "            \"Resource\": \"arn:aws:s3:::pi-cloud/*\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder().bucket("pi-cloud").config(policyJson).build());
        } else {
            log.info("Bucket 'pi-cloud' already exists.");
        }

        String extName = FileUtil.extName(file.getOriginalFilename());
        String uuid = IdUtil.simpleUUID();
        String fileName = "avatar/" + uuid + "." + extName;

        // Uploads data from a stream to an object.
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("pi-cloud")
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        String url =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket("pi-cloud")
                                .object(fileName)
                                .build());
        url = url.substring(0, url.indexOf("?"));

        System.out.println(url);

        userService.updateAvatarByUserName(username, url);

        String deleteFileName = avatar.substring(avatar.indexOf("pi-cloud") + "pi-cloud".length() + 1);
        minioClient.removeObject(RemoveObjectArgs.builder().bucket("pi-cloud").object(deleteFileName).build());
        return ResponseData.ok();
    }
}
