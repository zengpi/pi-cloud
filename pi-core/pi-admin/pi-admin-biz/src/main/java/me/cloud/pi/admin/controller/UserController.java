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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.cloud.pi.admin.pojo.dto.ProfileDTO;
import me.cloud.pi.admin.pojo.dto.UserDTO;
import me.cloud.pi.admin.pojo.dto.UserImportDTO;
import me.cloud.pi.admin.pojo.query.UserQuery;
import me.cloud.pi.admin.pojo.vo.OptionalUserVO;
import me.cloud.pi.admin.pojo.vo.UserInfoVO;
import me.cloud.pi.admin.pojo.vo.UserVO;
import me.cloud.pi.admin.service.UserService;
import me.cloud.pi.common.mybatis.base.BaseQuery;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.security.util.SecurityUtils;
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
@Tag(name = "用户管理")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "获取用户")
    @PreAuthorize("hasAuthority('sys_user_query')")
    public ResponseData<PiPage<UserVO>> getUsers(UserQuery userQuery) {
        return ResponseData.ok(userService.getUsers(userQuery));
    }

    @PostMapping
    @Operation(summary = "用户新增")
    @PreAuthorize("hasAuthority('sys_user_add')")
    public ResponseData<?> saveUser(@RequestBody @Valid UserDTO dto) {
        userService.saveUser(dto);
        return ResponseData.ok();
    }

    @PutMapping
    @Operation(summary = "编辑用户")
    @PreAuthorize("hasAuthority('sys_user_edit')")
    public ResponseData<?> updateUser(@RequestBody @Valid UserDTO dto) {
        userService.updateUser(dto);
        return ResponseData.ok();
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户")
    @PreAuthorize("hasAuthority('sys_user_delete')")
    public ResponseData<?> deleteUser(@PathVariable String ids) {
        userService.deleteUser(ids);
        return ResponseData.ok();
    }

    @GetMapping("/userExport")
    @Operation(summary = "用户导出")
    @PreAuthorize("hasAuthority('sys_user_export')")
    @SneakyThrows
    public void exportUser(UserQuery query, HttpServletResponse response) {
        userService.exportUser(query, response);
    }

    @GetMapping("/userImportTemp")
    @Operation(summary = "下载用户导入模板")
    @SneakyThrows
    public void downloadUserImportTemp(HttpServletResponse response) {
        userService.downloadUserImportTemp(response);
    }

    @PostMapping("/userImport")
    @Operation(summary = "用户导入")
    @PreAuthorize("hasAuthority('sys_user_import')")
    public ResponseData<?> importUser(UserImportDTO dto) {
        return ResponseData.ok(userService.importUser(dto));
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    public ResponseData<UserInfoVO> getUserInfo() {
        return ResponseData.ok(userService.getUserInfo(SecurityUtils.getUserName()));
    }

    @PutMapping("/profileEdit")
    @Operation(summary = "修改个人信息")
    public ResponseData<?> editProfile(@RequestBody ProfileDTO dto) {
        userService.editProfile(dto);
        return ResponseData.ok();
    }

    @PutMapping("/passReset/{id}")
    @Operation(summary = "密码重置")
    @PreAuthorize("hasAuthority('sys_user_pass_reset')")
    public ResponseData<?> resetPass(@PathVariable Long id) {
        userService.resetPass(id);
        return ResponseData.ok();
    }

    @GetMapping("/optionalUsers")
    @Operation(summary = "可选用户。如：为角色指定用户时，需要先查询出可以选择的用户列表，然后选择用户")
    public ResponseData<PiPage<OptionalUserVO>> getOptionalUsers(BaseQuery query) {
        return ResponseData.ok(userService.getOptionalUsers(query));
    }

    @PostMapping("/avatarUpload")
    @Operation(summary = "头像上传")
    @SneakyThrows
    public ResponseData<?> uploadAvatar(MultipartFile file, String username, String avatar) {
        userService.uploadAvatar(file, username, avatar);

        return ResponseData.ok();
    }
}
