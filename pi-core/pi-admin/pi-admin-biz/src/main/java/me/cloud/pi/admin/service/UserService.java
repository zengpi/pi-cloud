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

package me.cloud.pi.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.cloud.pi.admin.pojo.dto.UserEditDTO;
import me.cloud.pi.admin.pojo.dto.UserImportDTO;
import me.cloud.pi.admin.pojo.form.UserEditForm;
import me.cloud.pi.admin.pojo.form.UserForm;
import me.cloud.pi.admin.pojo.po.SysUser;
import me.cloud.pi.admin.pojo.query.UserQueryParam;
import me.cloud.pi.admin.pojo.vo.OptionalUserVO;
import me.cloud.pi.admin.pojo.vo.UserInfoVO;
import me.cloud.pi.admin.pojo.vo.UserVO;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.web.pojo.query.BaseQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
public interface UserService extends IService<SysUser> {
    /**
     * 用户查询
     *
     * @param query 查询条件
     * @return /
     */
    PiPage<UserVO> getUsers(UserQueryParam query);

    /**
     * 新增
     *
     * @param form 表单数据
     */
    void save(UserForm form);

    /**
     * 获取用户信息
     *
     * @return /
     */
    UserInfoVO getUserInfo();

    /**
     * 修改用户信息
     *
     * @param userEditDTO 待修改用户信息
     */
    void editPersonalInfo(UserEditDTO userEditDTO);

    /**
     * 删除
     *
     * @param ids 用户 ID，多个以逗号分隔
     */
    void delete(String ids);

    /**
     * 编辑用户
     *
     * @param userEditForm /
     */
    void edit(UserEditForm userEditForm);

    /**
     * 下载用户列表
     *
     * @param query    用户查询参数
     * @param response 响应
     * @throws IOException If the named encoding is not supported / if an input or output exception occurred
     */
    void export(UserQueryParam query, HttpServletResponse response) throws IOException;

    /**
     * 下载用户导入模板
     *
     * @param response 相应
     * @throws IOException /
     */
    void downloadUserImportTemp(HttpServletResponse response) throws IOException;

    /**
     * 导入用户
     *
     * @param dto 用户导入 DTO
     * @return 导入结果。
     */
    String importUser(UserImportDTO dto);

    /**
     * 密码重置
     *
     * @param id 待重置密码的用户 ID
     */
    void resetPass(Long id);

    /**
     * 可选用户。如：为角色指定用户时，需要先查询出可以选择的用户列表，然后选择用户
     *
     * @param query 查询条件
     * @return 可选用户列表
     */
    PiPage<OptionalUserVO> getOptionalUsers(BaseQueryParam query);

    /**
     * 根据用户名修改 avatar
     * @param username 用户名
     * @param avatar 头像路径
     */
    void updateAvatarByUserName(String username, String avatar);
}
