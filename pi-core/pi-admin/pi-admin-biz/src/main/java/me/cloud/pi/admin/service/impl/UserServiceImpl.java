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
import me.cloud.pi.admin.mapper.DeptMapper;
import me.cloud.pi.admin.mapper.UserMapper;
import me.cloud.pi.admin.pojo.dto.UserEditDTO;
import me.cloud.pi.admin.pojo.dto.UserImportDTO;
import me.cloud.pi.admin.pojo.form.UserEditForm;
import me.cloud.pi.admin.pojo.form.UserForm;
import me.cloud.pi.admin.pojo.po.*;
import me.cloud.pi.admin.pojo.query.UserQueryParam;
import me.cloud.pi.admin.pojo.vo.OptionalUserVO;
import me.cloud.pi.admin.pojo.vo.UserExportVO;
import me.cloud.pi.admin.pojo.vo.UserInfoVO;
import me.cloud.pi.admin.pojo.vo.UserVO;
import me.cloud.pi.admin.service.*;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.redis.constant.CacheConstants;
import me.cloud.pi.common.security.constant.SecurityConstants;
import me.cloud.pi.common.security.util.SecurityUtils;
import me.cloud.pi.common.util.ValidationUtil;
import me.cloud.pi.common.web.constant.FileConstants;
import me.cloud.pi.common.web.enums.ResponseStatus;
import me.cloud.pi.common.web.exception.BadRequestException;
import me.cloud.pi.common.mybatis.base.BaseQueryParam;
import me.cloud.pi.common.web.util.FileUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private static final String EXPORT_USER_FILE_NAME = "用户列表";
    private static final String IMPORT_USER_TEMPLATE_NAME = "用户导入模板";

    private final RoleService roleService;
    private final MenuService menuService;
    private final DeptService deptService;
    private final UserRoleService userRoleService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final DeptMapper deptMapper;
    @Resource
    @Lazy
    private UserServiceImpl userServiceImpl;

    @Override
    public PiPage<UserVO> getUsers(UserQueryParam query) {
        PiPage<UserVO> page = new PiPage<>(query.getPageNum(), query.getPageSize());
        return userMapper.user(page, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserForm form) {
        long userCount = super.count(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, form.getUsername()));
        if (userCount >= 1) {
            throw new BadRequestException(ResponseStatus.USER_NAME_HAS_EXISTS);
        }
        SysUser sysUser = userConverter.userFormToUserPo(form);

        sysUser.setPassword(passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD));

        if (form.getDeptId() != null) {
            // 查询部门是否存在
            long count = deptService.count(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getId, form.getDeptId()));
            if (count <= 0) {
                throw new BadRequestException(ResponseStatus.INVALID_USER_INPUT, "指定的部门不存在");
            }
            sysUser.setDeptId(form.getDeptId());
        }

        super.save(sysUser);

        if (CollUtil.isNotEmpty(form.getRoleIds())) {
            long count = roleService.count(Wrappers.lambdaQuery(SysRole.class).in(SysRole::getId, form.getRoleIds()));
            if (count != form.getRoleIds().size()) {
                throw new BadRequestException(ResponseStatus.INVALID_USER_INPUT, "指定的角色不存在");
            }
            ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
            form.getRoleIds().forEach(e -> sysUserRoles.add(new SysUserRole(sysUser.getId(), e)));
            userRoleService.saveBatch(sysUserRoles);
        }
    }

    @Override
    public UserInfoVO getUserInfo(){
        return userServiceImpl.getUserInfo(SecurityUtils.getUserName());
    }

    @Override
    @Cacheable(CacheConstants.CACHE_USER)
    public UserInfoVO getUserInfo(String username) {
        UserInfoVO userInfoVO = new UserInfoVO();

        // 用户信息
        SysUser sysUser = super.getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUsername, username)
                .select(SysUser::getId, SysUser::getUsername, SysUser::getNickname, SysUser::getAvatar, SysUser::getPhone));
        userInfoVO.setUserInfo(userConverter.userPoToUserInfo(sysUser));

        if (sysUser == null) {
            return userInfoVO;
        }
        // 角色
        List<SysRole> roleList = roleService.listRoleByUserId(sysUser.getId());
        String[] roles = roleList.stream().map(SysRole::getRoleCode).toArray(String[]::new);
        userInfoVO.setRoles(roles);

        // 权限标识
        Long[] roleIds = roleList.stream().map(SysRole::getId).toArray(Long[]::new);

        if (roleIds.length == 0) {
            return userInfoVO;
        }

        List<SysMenu> menuList = menuService.listPermissionByRoleIds(roleIds);
        String[] permissions = menuList.stream().map(SysMenu::getPermission).toArray(String[]::new);
        userInfoVO.setAuthorities(permissions);

        return userInfoVO;
    }

    @Override
    @CacheEvict(value = CacheConstants.CACHE_USER, allEntries = true)
    public void editPersonalInfo(UserEditDTO userEditDTO) {
        // 获取当前用户名
        String userName = SecurityUtils.getUserName();

        // 根据用户名获取当前用户信息
        SysUser sysUser = super.getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUsername, userName)
                .select(SysUser::getId, SysUser::getPassword, SysUser::getPhone, SysUser::getNickname));

        // 如果要修改密码，判断旧密码是否正确
        if (!StrUtil.isBlank(userEditDTO.getPassword())) {
            if (passwordEncoder.matches(userEditDTO.getOldPassword(), sysUser.getPassword())) {
                sysUser.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));
            } else {
                throw new BadRequestException(ResponseStatus.OLD_PASSWORD_INCORRECT);
            }
        }

        sysUser.setPhone(userEditDTO.getPhone());
        sysUser.setNickname(userEditDTO.getNickname());

        // 更新
        super.updateById(sysUser);
    }

    @Override
    public void delete(String ids) {
        String[] split = ids.split(",");
        List<Long> idList = Arrays.stream(split).map(Long::valueOf).collect(Collectors.toList());

        super.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.CACHE_USER, allEntries = true)
    public void edit(UserEditForm userEditForm) {
        if (userEditForm.getId() == null) {
            throw new BadRequestException(ResponseStatus.REQUEST_PARAM_ERROR, "id 不能为空");
        }

        // 当前用户所有角色 ID
        List<Long> roles = userRoleService.list(Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId, userEditForm.getId())
                .select(SysUserRole::getRoleId)
        ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        // 待删除角色列表
        List<Long> toDelIds = roles.stream()
                .filter(role -> !userEditForm.getRoleIds().contains(role))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(toDelIds)) {
            userRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class)
                    .eq(SysUserRole::getUserId, userEditForm.getId())
                    .in(SysUserRole::getRoleId, toDelIds)
            );
        }

        // 待新增角色列表
        List<Long> toAddIds = userEditForm.getRoleIds().stream()
                .filter(role -> !roles.contains(role))
                .collect(Collectors.toList());
        List<SysUserRole> toAddEntities = toAddIds.stream()
                .map(e -> new SysUserRole(userEditForm.getId(), e))
                .collect(Collectors.toList());
        userRoleService.saveBatch(toAddEntities);

        SysUser sysUser = userConverter.userEditFormToUserPo(userEditForm);

        super.updateById(sysUser);
    }

    @Override
    @SneakyThrows
    public void export(UserQueryParam query, HttpServletResponse response) {
        PiPage<UserExportVO> page = new PiPage<>(query.getPageNum(), query.getPageSize());
        List<UserExportVO> exportData = userMapper.listDownloadRecode(page, query);

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
            InputStream in = this.getClass()
                    .getClassLoader()
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
        // 导入用户检查
        importUserCheck(dto);

        /*
        解析 excel，构造用户实体。
         */
        if (dto.getFile() == null) {
            throw new BadRequestException("请选择要导入的文件");
        }
        // 数据量少，同步获取数据
        List<UserImportDTO.FileItem> fileItems = EasyExcel.read(dto.getFile().getInputStream())
                .head(UserImportDTO.FileItem.class).sheet().doReadSync();

        // 校验文件数据
        checkFileData(fileItems);

        // 构造用户实体
        ArrayList<SysUser> users = new ArrayList<>();
        StringBuilder warningMsg = new StringBuilder();
        fileItems.forEach(e -> {
            Integer isExists = userMapper.userNameExists(e.getUsername());
            if (isExists != null) {
                warningMsg.append("用户名 ").append(e.getUsername()).append(" 已存在，已跳过处理；");
                return;
            }
            SysUser user = userConverter.fileItemToUserPo(e);
            user.setPassword(passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD));
            user.setDeptId(dto.getDeptId());
            users.add(user);
        });

        super.saveBatch(users);

        // 构造用户角色实体
        ArrayList<SysUserRole> userRoles = new ArrayList<>();
        users.stream()
                .map(SysUser::getId)
                .collect(Collectors.toList())
                .forEach(userId -> {
                    for (Long roleId : dto.getRoleIds()) {
                        SysUserRole userRole = new SysUserRole();
                        userRole.setUserId(userId);
                        userRole.setRoleId(roleId);
                        userRoles.add(userRole);
                    }
                });

        userRoleService.saveBatch(userRoles);

        return warningMsg.append(StrUtil.format("共 {} 条数据，成功导入 {} 条数据，导入失败 {} 条数据",
                fileItems.size(), users.size(), fileItems.size() - users.size())).toString();
    }

    @Override
    public void resetPass(Long id) {
        String pass = passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD);
        super.update(Wrappers.lambdaUpdate(SysUser.class)
                .set(SysUser::getPassword, pass)
                .eq(SysUser::getId, id));
    }

    @Override
    public PiPage<OptionalUserVO> getOptionalUsers(BaseQueryParam query) {
        return userMapper.getOptionalUsers(new PiPage<>(query.getPageNum(), query.getPageSize()), query);
    }

    @Override
    public void updateAvatarByUserName(String username, String avatar) {
        super.update(Wrappers.lambdaUpdate(SysUser.class)
                .eq(SysUser::getUsername, username)
                .set(SysUser::getAvatar, avatar));
    }

    /**
     * 校验文件数据
     *
     * @param fileItems 文件数据记录
     */
    private void checkFileData(List<UserImportDTO.FileItem> fileItems) {
        if (fileItems.size() == 0) {
            throw new BadRequestException("未检测到任何数据");
        }
        List<String> usernames = fileItems.stream().map(UserImportDTO.FileItem::getUsername).collect(Collectors.toList());
        long validCount = usernames.stream().distinct().count();
        Assert.isTrue(validCount == fileItems.size(), "导入数据中存在重复的用户名，请检查。");

        StringBuilder errMsg = new StringBuilder();

        for (int i = 0; i < fileItems.size(); i++) {
            if (StrUtil.isBlank(fileItems.get(i).getUsername())) {
                errMsg.append("提交的数据中，第 ").append(i + 1).append(" 条记录用户名为空，请检查。");
            }
            if (StrUtil.isBlank(fileItems.get(i).getNickname())) {
                errMsg.append("提交的数据中，第 ").append(i + 1).append(" 条记录昵称为空，请检查。");
            }
            if (StrUtil.isNotBlank(fileItems.get(i).getPhone())) {
                if (!ValidationUtil.checkPhone(fileItems.get(i).getPhone())) {
                    errMsg.append("提交的数据中，第 ").append(i + 1).append(" 条记录手机号不符合规范，请检查。");
                }
            }
        }

        if (errMsg.length() > 0) {
            throw new BadRequestException(errMsg.toString());
        }
    }

    /**
     * 导入用户检查
     *
     * @param dto 用户导入 DTO
     */
    private void importUserCheck(UserImportDTO dto) {
        // 判断部门是否存在
        if (dto.getDeptId() != null && dto.getDeptId() > 0) {
            Integer isExists = deptMapper.exists(dto.getDeptId());
            if (isExists == null) {
                throw new BadRequestException("指定的部门不存在");
            }
        }

        // 判断集合中的角色 ID 是否存在于角色表中
        if (CollUtil.isNotEmpty(dto.getRoleIds())) {
            List<Long> dbUserIds = roleService.list(Wrappers.lambdaQuery(SysRole.class)
                    .in(SysRole::getId, dto.getRoleIds())
                    .select(SysRole::getId))
                    .stream()
                    .map(SysRole::getId)
                    .collect(Collectors.toList());
            List<Long> notExists = dto.getRoleIds().stream().filter(e -> !dbUserIds.contains(e)).collect(Collectors.toList());
            if (notExists.size() > 0) {
                throw new BadRequestException("角色 ID " + notExists + " 不存在");
            }
        }
    }
}
