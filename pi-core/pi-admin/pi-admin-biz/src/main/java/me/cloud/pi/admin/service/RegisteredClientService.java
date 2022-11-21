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
import me.cloud.pi.admin.pojo.dto.RegisteredClientDTO;
import me.cloud.pi.admin.pojo.po.SysRegisteredClient;
import me.cloud.pi.admin.pojo.vo.RegisteredClientVO;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.mybatis.base.BaseQueryParam;

/**
 * @author ZnPi
 * @date 2022-11-14
 */
public interface RegisteredClientService extends IService<SysRegisteredClient> {
    /**
     * 获取客户端
     *
     * @param queryParam 查询条件
     * @return 客户端列表
     */
    PiPage<RegisteredClientVO> getClients(BaseQueryParam queryParam);

    /**
     * 保存或更新客户端
     *
     * @param dto 客户端信息
     */
    void saveOrUpdate(RegisteredClientDTO dto);

    /**
     * 删除客户端
     * @param ids /
     */
    void delClient(String ids);
}
