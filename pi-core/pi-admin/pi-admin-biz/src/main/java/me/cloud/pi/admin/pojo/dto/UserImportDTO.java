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
