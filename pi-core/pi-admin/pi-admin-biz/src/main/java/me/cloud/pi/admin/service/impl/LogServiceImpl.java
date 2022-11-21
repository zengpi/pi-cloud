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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.cloud.pi.admin.api.dto.LogDTO;
import me.cloud.pi.admin.converter.LogConverter;
import me.cloud.pi.admin.mapper.LogMapper;
import me.cloud.pi.admin.pojo.po.SysLog;
import me.cloud.pi.admin.pojo.query.LogQueryParam;
import me.cloud.pi.admin.pojo.vo.LogExportVO;
import me.cloud.pi.admin.pojo.vo.LogVO;
import me.cloud.pi.admin.service.LogService;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.web.constant.FileConstants;
import me.cloud.pi.common.web.exception.BadRequestException;
import me.cloud.pi.common.web.util.FileUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-11-19
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, SysLog> implements LogService {
    private static final String EXPORT_LOG_FILE_NAME = "日志导出";

    private final LogConverter logConverter;
    private final LogMapper logMapper;

    @Override
    public void saveLog(LogDTO dto) {
        SysLog sysLog = logConverter.dtoToPo(dto);
        super.save(sysLog);
    }

    @Override
    public PiPage<LogVO> getLogs(LogQueryParam queryParam) {
        LambdaQueryWrapper<SysLog> wrapper = Wrappers.lambdaQuery(SysLog.class);
        PiPage<SysLog> page = new PiPage<>(queryParam.getPageNum(), queryParam.getPageSize());
        if (queryParam.getQueryColumn() != null && StrUtil.isNotBlank(queryParam.getKeyWord())) {
            wrapper.like(LogQueryParam.LogColumn.TITLE.getColumn().equals(queryParam.getQueryColumn()),
                            SysLog::getTitle, queryParam.getKeyWord())
                    .like(LogQueryParam.LogColumn.CREATE_BY.getColumn().equals(queryParam.getQueryColumn()),
                            SysLog::getCreateBy, queryParam.getKeyWord())
                    .like(LogQueryParam.LogColumn.METHOD_NAME.getColumn().equals(queryParam.getQueryColumn()),
                            SysLog::getMethodName, queryParam.getKeyWord());
        }

        if (ArrayUtil.isNotEmpty(queryParam.getCreateTime()) && queryParam.getCreateTime().length > 1) {
            wrapper.ge(SysLog::getCreateTime, queryParam.getCreateTime()[0])
                    .le(SysLog::getCreateTime, queryParam.getCreateTime()[1]);
        }
        PiPage<SysLog> rst = super.page(page, wrapper.eq(queryParam.getType() != null, SysLog::getType, queryParam.getType())
                .select(SysLog::getId, SysLog::getCreateTime, SysLog::getCreateBy, SysLog::getType, SysLog::getIp,
                        SysLog::getTitle, SysLog::getExceptionDesc, SysLog::getRequestMethod,
                        SysLog::getRequestParam, SysLog::getRequestTime, SysLog::getMethodName));
        return logConverter.pagePoToPageVo(rst);
    }

    @Override
    public void delLog(String ids) {
        if (StrUtil.isBlank(ids)) {
            throw new BadRequestException("ids 不能为空");
        }
        Set<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        super.removeByIds(idSet);
    }

    @Override
    @SneakyThrows
    public void export(LogQueryParam queryParam, HttpServletResponse response) {
        PiPage<LogExportVO> page = new PiPage<>(queryParam.getPageNum(), queryParam.getPageSize());
        List<LogExportVO> exportData = logMapper.listExportLog(page, queryParam);
        FileUtil.export(response, EXPORT_LOG_FILE_NAME, FileConstants.XLSX_SUFFIX, () -> {
            try{
                EasyExcel.write(response.getOutputStream(), LogExportVO.class)
                        .sheet(EXPORT_LOG_FILE_NAME).doWrite(exportData);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });
    }
}
