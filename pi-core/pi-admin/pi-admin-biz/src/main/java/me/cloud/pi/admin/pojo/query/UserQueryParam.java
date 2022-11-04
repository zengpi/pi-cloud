package me.cloud.pi.admin.pojo.query;

import lombok.Getter;
import lombok.Setter;
import me.cloud.pi.common.web.pojo.query.BaseQueryParam;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Getter
@Setter
public class UserQueryParam extends BaseQueryParam {
    /**
     * 用户状态
     */
    private Integer enabled;
    /**
     * 部门 ID
     */
    private Long deptId;
}
