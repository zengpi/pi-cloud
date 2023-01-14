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

package me.pi.cloud.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.pi.cloud.admin.pojo.dto.DeptDTO;
import me.pi.cloud.admin.pojo.po.SysDept;
import me.pi.cloud.admin.pojo.query.DeptTreeQuery;
import me.pi.cloud.admin.pojo.vo.DeptTreeVO;
import me.pi.cloud.common.web.vo.SelectTreeVO;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
public interface DeptService extends IService<SysDept> {
    /**
     * 获取部门（树形）
     *
     * @param query 查询参数
     * @return 部门（树形）
     */
    List<DeptTreeVO> getDeptTree(DeptTreeQuery query);

    /**
     * 新增或编辑部门
     *
     * @param dto DeptDTO
     */
    void saveOrUpdate(DeptDTO dto);

    /**
     * 删除部门
     *
     * @param ids 待删除记录的 ID 列表，以逗号分隔
     */
    void deleteDept(String ids);

    /**
     * 获取部门选择器（树形）
     *
     * @return 部门选择器（树形）
     */
    List<SelectTreeVO> getDeptSelectTree();

    /**
     * 指定的 DeptId 是否存在
     *
     * @param deptId 部门 ID
     * @return 1：存在；null：不存在
     */
    Integer existsByDeptId(Long deptId);
}
