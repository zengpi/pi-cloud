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

package me.cloud.pi.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.api.dto.LogDTO;
import me.cloud.pi.admin.pojo.query.LogQueryParam;
import me.cloud.pi.admin.service.LogService;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ZnPi
 * @date 2022-11-19
 */
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@Tag(name = "日志管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class LogController {
    private final LogService logService;

    @GetMapping
    @Operation(summary = "获取日志")
    public ResponseData<?> getLogs(LogQueryParam queryParam){
        return ResponseData.ok(logService.getLogs(queryParam));
    }

    @PostMapping
    @Operation(summary = "保存日志")
    public ResponseData<?> saveLog(@RequestBody LogDTO dto) {
        logService.saveLog(dto);
        return ResponseData.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除日志")
    public ResponseData<?> delLog(String ids){
        logService.delLog(ids);
        return ResponseData.ok();
    }

    @GetMapping("/export")
    @Operation(summary = "导出日志")
    public void export(LogQueryParam queryParam, HttpServletResponse response){
        logService.export(queryParam, response);
    }
}
