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

package me.cloud.pi.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.cloud.pi.admin.converter.UserConverter;
import me.cloud.pi.admin.mapper.UserMapper;
import me.cloud.pi.admin.pojo.dto.UserDTO;
import me.cloud.pi.admin.pojo.dto.ProfileDTO;
import me.cloud.pi.admin.pojo.dto.UserImportDTO;
import me.cloud.pi.admin.pojo.po.*;
import me.cloud.pi.admin.pojo.query.RoleMemberQuery;
import me.cloud.pi.admin.pojo.query.UserQuery;
import me.cloud.pi.admin.pojo.vo.*;
import me.cloud.pi.admin.service.*;
import me.cloud.pi.common.file.handler.MinioHandler;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.redis.constant.CacheConstants;
import me.cloud.pi.common.security.constant.SecurityConstants;
import me.cloud.pi.common.security.util.SecurityUtils;
import me.cloud.pi.common.util.ValidationUtil;
import me.cloud.pi.common.web.constant.FileConstants;
import me.cloud.pi.common.web.enums.ResponseStatus;
import me.cloud.pi.common.web.exception.BadRequestException;
import me.cloud.pi.common.mybatis.base.BaseQuery;
import me.cloud.pi.common.web.util.FileUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {
    private static final String EXPORT_USER_FILE_NAME = "????????????";
    private static final String IMPORT_USER_TEMPLATE_NAME = "??????????????????";

    private final UserMapper userMapper;

    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final MenuService menuService;
    private final DeptService deptService;
    private final UserRoleService userRoleService;

    private final MinioHandler minioHandler;

    @Override
    public PiPage<UserVO> getUsers(UserQuery query) {
        PiPage<UserVO> page = new PiPage<>(query.getPageNum(), query.getPageSize());
        return userMapper.listUsers(page, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO dto) {
        // ???????????????????????????
        Integer exists = userMapper.existsByUsername(dto.getUsername());
        if (exists != null) {
            throw new BadRequestException(ResponseStatus.USER_NAME_HAS_EXISTS);
        }

        SysUser sysUser = userConverter.userDtoToSysUser(dto);

        if(StrUtil.isBlank(sysUser.getPassword())){
            sysUser.setPassword(passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD));
        } else{
            sysUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getDeptId() != null) {
            // ????????????????????????
            Integer roleExists = deptService.existsByDeptId(dto.getDeptId());
            if (roleExists == null) {
                throw new BadRequestException(ResponseStatus.INVALID_USER_INPUT, "????????????????????????");
            }
        }

        super.save(sysUser);

        if (CollUtil.isNotEmpty(dto.getRoleIds())) {
            long count = roleService.count(Wrappers.lambdaQuery(SysRole.class).in(SysRole::getId, dto.getRoleIds()));
            if (count != dto.getRoleIds().size()) {
                throw new BadRequestException(ResponseStatus.INVALID_USER_INPUT, "????????????????????????");
            }
            ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
            dto.getRoleIds().forEach(e -> sysUserRoles.add(new SysUserRole(sysUser.getId(), e)));
            userRoleService.saveBatch(sysUserRoles);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.CACHE_USER, allEntries = true)
    public void updateUser(UserDTO dto) {
        if (dto.getId() == null) {
            throw new BadRequestException(ResponseStatus.REQUEST_PARAM_ERROR, "id ????????????");
        }

        // ???????????????????????? ID
        List<Long> roleList = userRoleService.list(Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId, dto.getId())
                .select(SysUserRole::getRoleId)
        ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        // ?????????????????????
        List<Long> toDeleteIds = roleList.stream()
                .filter(role -> !dto.getRoleIds().contains(role))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(toDeleteIds)) {
            userRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class)
                    .eq(SysUserRole::getUserId, dto.getId())
                    .in(SysUserRole::getRoleId, toDeleteIds)
            );
        }

        // ?????????????????????
        List<Long> toAddIds = dto.getRoleIds().stream()
                .filter(role -> !roleList.contains(role))
                .collect(Collectors.toList());
        List<SysUserRole> toAddEntities = toAddIds.stream()
                .map(e -> new SysUserRole(dto.getId(), e))
                .collect(Collectors.toList());
        userRoleService.saveBatch(toAddEntities);

        SysUser sysUser = userConverter.userDtoToSysUser(dto);

        super.updateById(sysUser);
    }

    @Override
    public void deleteUser(String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());

        super.removeByIds(idList);
    }

    @Override
    @SneakyThrows
    public void exportUser(UserQuery query, HttpServletResponse response) {
        PiPage<UserExportVO> page = new PiPage<>(query.getPageNum(), query.getPageSize());
        List<UserExportVO> exportData = userMapper.listExportRecode(page, query);

        FileUtil.export(response, EXPORT_USER_FILE_NAME, FileConstants.XLSX_SUFFIX, () -> {
            try {
                EasyExcel.write(response.getOutputStream(), UserExportVO.class)
                        .sheet(EXPORT_USER_FILE_NAME).doWrite(exportData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @SneakyThrows
    public void downloadUserImportTemp(HttpServletResponse response) {
        FileUtil.export(response, IMPORT_USER_TEMPLATE_NAME, FileConstants.XLSX_SUFFIX, () -> {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(FileConstants.TEMPLATE_PATH + File.separator
                            + IMPORT_USER_TEMPLATE_NAME + FileConstants.XLSX_SUFFIX);
            try {
                EasyExcel.write(response.getOutputStream())
                        .withTemplate(in)
                        .sheet(IMPORT_USER_TEMPLATE_NAME)
                        .doWrite(Collections.emptyList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importUser(UserImportDTO dto) {
        // ??????????????????
        importUserCheck(dto);

        /*
        ?????? excel????????????????????????
         */
        if (dto.getFile() == null) {
            throw new BadRequestException("???????????????????????????");
        }
        // ?????????????????????????????????
        List<UserImportDTO.FileItem> fileItems = EasyExcel.read(dto.getFile().getInputStream())
                .head(UserImportDTO.FileItem.class).sheet().doReadSync();

        // ??????????????????
        checkFileData(fileItems);

        // ??????????????????
        ArrayList<SysUser> users = new ArrayList<>();
        StringBuilder tipMsg = new StringBuilder();
        fileItems.forEach(e -> {
            Integer exists = userMapper.existsByUsername(e.getUsername());
            if (exists != null) {
                tipMsg.append("????????? ").append(e.getUsername()).append(" ??????????????????????????????");
                return;
            }
            SysUser user = userConverter.fileItemToSysUser(e);
            user.setPassword(passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD));
            user.setDeptId(dto.getDeptId());
            users.add(user);
        });

        super.saveBatch(users);

        // ????????????????????????
        ArrayList<SysUserRole> userRoles = new ArrayList<>();
        users.stream()
                .map(SysUser::getId)
                .collect(Collectors.toList())
                .forEach(userId -> {
                    dto.getRoleIds().forEach((roleId) -> {
                        SysUserRole userRole = new SysUserRole();
                        userRole.setUserId(userId);
                        userRole.setRoleId(roleId);
                        userRoles.add(userRole);
                    });
                });

        userRoleService.saveBatch(userRoles);

        return tipMsg.append(StrUtil.format("??? {} ???????????????????????? {} ???????????????????????? {} ?????????",
                fileItems.size(), users.size(), fileItems.size() - users.size())).toString();
    }

    @Override
    @Cacheable(CacheConstants.CACHE_USER)
    public UserInfoVO getUserInfo(String username) {
        UserInfoVO userInfoVO = new UserInfoVO();

        // ????????????
        SysUser sysUser = super.getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUsername, username)
                .select(SysUser::getId, SysUser::getUsername, SysUser::getNickname, SysUser::getAvatar, SysUser::getPhone));
        userInfoVO.setUserInfo(userConverter.sysUserToUserInfo(sysUser));

        if (sysUser == null) {
            throw new BadRequestException(ResponseStatus.CLIENT_ERROR, "???????????????");
        }

        // ??????
        List<SysRole> roleList = roleService.listRoleByUserId(sysUser.getId());
        String[] roles = roleList.stream().map(SysRole::getRoleCode).toArray(String[]::new);
        userInfoVO.setRoles(roles);

        // ????????????
        Long[] roleIds = roleList.stream().map(SysRole::getId).toArray(Long[]::new);

        if (roleIds.length == 0) {
            return userInfoVO;
        }

        String[] permissions = menuService.listPermissionByRoleIds(roleIds)
                .stream().map(SysMenu::getPermission).toArray(String[]::new);
        userInfoVO.setAuthorities(permissions);

        return userInfoVO;
    }

    @Override
    @CacheEvict(value = CacheConstants.CACHE_USER, allEntries = true)
    public void editProfile(ProfileDTO dto) {
        // ?????????????????????
        String userName = SecurityUtils.getUserName();

        // ???????????????????????????????????????
        SysUser sysUser = super.getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUsername, userName)
                .select(SysUser::getId, SysUser::getPassword, SysUser::getPhone, SysUser::getNickname));

        // ???????????????????????????????????????????????????
        if (!StrUtil.isBlank(dto.getPassword())) {
            if (passwordEncoder.matches(dto.getOldPassword(), sysUser.getPassword())) {
                sysUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            } else {
                throw new BadRequestException(ResponseStatus.OLD_PASSWORD_INCORRECT);
            }
        }

        sysUser.setPhone(dto.getPhone());
        sysUser.setNickname(dto.getNickname());

        // ??????
        super.updateById(sysUser);
    }

    @Override
    public void resetPass(Long id) {
        super.update(Wrappers.lambdaUpdate(SysUser.class)
                .set(SysUser::getPassword, passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD))
                .eq(SysUser::getId, id));
    }

    @Override
    public PiPage<OptionalUserVO> getOptionalUsers(BaseQuery query) {
        return userMapper.listOptionalUsers(new PiPage<>(query.getPageNum(), query.getPageSize()), query);
    }

    @CacheEvict(value = CacheConstants.CACHE_USER, allEntries = true)
    @Override
    public void uploadAvatar(MultipartFile file, String username, String avatar) {
        String url = minioHandler.putObject(file);

        super.update(Wrappers.lambdaUpdate(SysUser.class)
                .eq(SysUser::getUsername, username)
                .set(SysUser::getAvatar, url));

        String deleteFileName = avatar.substring(avatar.indexOf("pi-cloud") + "pi-cloud".length() + 1);
        minioHandler.removeObject(deleteFileName);
    }

    @Override
    public PiPage<RoleMemberVO> getRoleMembers(RoleMemberQuery query) {
        return userMapper
                .getRoleMembers(new PiPage<>(query.getPageNum(), query.getPageSize()), query);
    }

    /**
     * ??????????????????
     *
     * @param dto ???????????? DTO
     */
    private void importUserCheck(UserImportDTO dto) {
        // ????????????????????????
        if (dto.getDeptId() != null) {
            Integer isExists = deptService.existsByDeptId(dto.getDeptId());
            if (isExists == null) {
                throw new BadRequestException("????????????????????????");
            }
        }

        // ???????????????????????? ID ???????????????????????????
        if (CollUtil.isNotEmpty(dto.getRoleIds())) {
            List<Long> roleIdList = roleService.list(Wrappers.lambdaQuery(SysRole.class)
                            .in(SysRole::getId, dto.getRoleIds())
                            .select(SysRole::getId))
                    .stream()
                    .map(SysRole::getId)
                    .collect(Collectors.toList());
            List<Long> notExists = dto.getRoleIds().stream().filter(e -> !roleIdList.contains(e)).collect(Collectors.toList());
            if (notExists.size() > 0) {
                throw new BadRequestException("?????? ID " + notExists + " ?????????");
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param fileItems ??????????????????
     */
    private void checkFileData(List<UserImportDTO.FileItem> fileItems) {
        if (fileItems.size() == 0) {
            throw new BadRequestException("????????????????????????");
        }
        List<String> usernames = fileItems.stream().map(UserImportDTO.FileItem::getUsername).collect(Collectors.toList());
        long validCount = usernames.stream().distinct().count();
        Assert.isTrue(validCount == fileItems.size(), "??????????????????????????????????????????????????????");

        StringBuilder errMsg = new StringBuilder();

        for (int i = 0; i < fileItems.size(); i++) {
            if (StrUtil.isBlank(fileItems.get(i).getUsername())) {
                errMsg.append("???????????????????????? ").append(i + 1).append(" ???????????????????????????????????????");
            }
            if (StrUtil.isBlank(fileItems.get(i).getNickname())) {
                errMsg.append("???????????????????????? ").append(i + 1).append(" ????????????????????????????????????");
            }
            if (StrUtil.isNotBlank(fileItems.get(i).getPhone())) {
                if (!ValidationUtil.checkPhone(fileItems.get(i).getPhone())) {
                    errMsg.append("???????????????????????? ").append(i + 1).append(" ????????????????????????????????????????????????");
                }
            }
        }

        if (errMsg.length() > 0) {
            throw new BadRequestException(errMsg.toString());
        }
    }
}
