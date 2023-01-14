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

package me.pi.cloud.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import me.pi.cloud.admin.pojo.dto.ProfileDTO;
import me.pi.cloud.admin.pojo.dto.UserDTO;
import me.pi.cloud.admin.pojo.dto.UserImportDTO;
import me.pi.cloud.admin.pojo.po.SysUser;
import me.pi.cloud.admin.pojo.query.RoleMemberQuery;
import me.pi.cloud.admin.pojo.query.UserQuery;
import me.pi.cloud.admin.pojo.vo.OptionalUserVO;
import me.pi.cloud.admin.pojo.vo.RoleMemberVO;
import me.pi.cloud.admin.pojo.vo.UserInfoVO;
import me.pi.cloud.admin.pojo.vo.UserVO;
import me.pi.cloud.common.mybatis.base.BaseQuery;
import me.pi.cloud.common.mybatis.util.PiPage;
import org.springframework.web.multipart.MultipartFile;

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
     * @return 用户
     */
    PiPage<UserVO> getUsers(UserQuery query);

    /**
     * 新增用户
     *
     * @param dto UserDTO
     */
    void saveUser(UserDTO dto);

    /**
     * 编辑用户
     *
     * @param dto UserDTO
     */
    void updateUser(UserDTO dto);

    /**
     * 删除用户
     *
     * @param ids 待删除用户 ID，多个以逗号分隔
     */
    void deleteUser(String ids);

    /**
     * 用户导出
     *
     * @param query    用户查询参数
     * @param response 响应
     * @throws IOException If the named encoding is not supported / if an input or output exception occurred
     */
    void exportUser(UserQuery query, HttpServletResponse response) throws IOException;

    /**
     * 下载用户导入模板
     *
     * @param response HttpServletResponse
     * @throws IOException If the named encoding is not supported / if an input or output exception occurred
     */
    void downloadUserImportTemp(HttpServletResponse response) throws IOException;


    /**
     * 用户导入
     *
     * @param dto 用户导入 DTO
     * @return 导入结果
     */
    String importUser(UserImportDTO dto);

    /**
     * 获取用户信息
     *
     * @param username 用户名。用于缓存 key
     * @return /
     */
    UserInfoVO getUserInfo(String username);

    /**
     * 修改用户个人信息
     *
     * @param profileDTO 待修改用户个人信息
     */
    void editProfile(ProfileDTO profileDTO);

    /**
     * 密码重置，默认密码为 123456
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
    PiPage<OptionalUserVO> getOptionalUsers(BaseQuery query);

    /**
     * 头像上传
     * @param file 头像
     * @param username 用户名
     * @param avatar 旧头像名称
     */
    void uploadAvatar(MultipartFile file, String username, String avatar);


    /**
     * 角色成员
     *
     * @param query 查询参数
     * @return 角色成员
     */
    PiPage<RoleMemberVO> getRoleMembers(RoleMemberQuery query);
}
