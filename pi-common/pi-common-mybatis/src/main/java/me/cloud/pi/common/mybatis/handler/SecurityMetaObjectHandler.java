package me.cloud.pi.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.cloud.pi.common.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
public class SecurityMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "createBy", SecurityUtils::getUserName, String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
        this.strictUpdateFill(metaObject, "updateBy", SecurityUtils::getUserName, String.class);
    }
}
