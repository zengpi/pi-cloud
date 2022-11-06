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

package me.cloud.pi.admin.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户导入 DTO
 * @author ZnPi
 * @date 2022-09-15
 */
@Data
public class UserImportDTO {
    /**
     * 部门 ID
     */
    private Long deptId;
    /**
     * 角色 ID 列表
     */
    private List<Long> roleIds;
    /**
     * 文件
     */
    private MultipartFile file;

    @Data
    public static class FileItem{
        /**
         * 用户名
         */
        private String username;
        /**
         * 昵称
         */
        private String nickname;
        /**
         * 手机
         */
        private String phone;
    }
}
