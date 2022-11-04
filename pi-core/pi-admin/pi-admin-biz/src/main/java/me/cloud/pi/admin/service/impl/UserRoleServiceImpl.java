package me.cloud.pi.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.cloud.pi.admin.mapper.UserRoleMapper;
import me.cloud.pi.admin.pojo.po.SysUserRole;
import me.cloud.pi.admin.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRole> implements UserRoleService {
}
