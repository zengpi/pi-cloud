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
