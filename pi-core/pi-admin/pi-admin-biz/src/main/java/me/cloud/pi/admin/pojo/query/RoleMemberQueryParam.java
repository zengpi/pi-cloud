package me.cloud.pi.admin.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cloud.pi.common.web.pojo.query.BaseQueryParam;

import javax.validation.constraints.NotNull;

/**
 * @author ZnPi
 * @date 2022-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMemberQueryParam extends BaseQueryParam {
    /**
     * 角色 ID
     */
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;
}
