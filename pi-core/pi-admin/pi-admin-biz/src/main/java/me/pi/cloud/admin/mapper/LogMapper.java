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

package me.pi.cloud.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.pi.cloud.admin.pojo.po.SysLog;
import me.pi.cloud.admin.pojo.query.LogQuery;
import me.pi.cloud.admin.pojo.vo.LogExportVO;
import me.pi.cloud.common.mybatis.util.PiPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-11-19
 */
public interface LogMapper extends BaseMapper<SysLog> {
    /**
     * 查询导出日志记录
     *
     * @param page       /
     * @param queryParam /
     * @return /
     */
    List<LogExportVO> listExportLog(@Param("page") PiPage<LogExportVO> page, @Param("query") LogQuery queryParam);
}
