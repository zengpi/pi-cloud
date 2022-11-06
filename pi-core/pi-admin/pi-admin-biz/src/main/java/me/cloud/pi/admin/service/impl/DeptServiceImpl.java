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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.converter.DeptConverter;
import me.cloud.pi.admin.mapper.DeptMapper;
import me.cloud.pi.admin.pojo.po.SysDept;
import me.cloud.pi.admin.pojo.vo.DeptTreeVO;
import me.cloud.pi.admin.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, SysDept> implements DeptService {
    private final DeptConverter deptConverter;

    @Override
    public List<DeptTreeVO> getDeptTree() {
        List<SysDept> sysDeptList = super.list(Wrappers.lambdaQuery(SysDept.class)
                .select(SysDept::getId, SysDept::getName, SysDept::getParentId));
        return buildDeptTree(0L, sysDeptList);
    }

    private List<DeptTreeVO> buildDeptTree(Long parentId, List<SysDept> sysDeptList){
        List<DeptTreeVO> deptTreeList = new ArrayList<>();
        sysDeptList.stream()
                .filter(sysDept -> sysDept.getParentId().equals(parentId))
                .forEach(sysDept -> {
                    DeptTreeVO deptTreeVO = deptConverter.sysDeptPoToDeptTreeVo(sysDept);
                    deptTreeVO.setChildren(buildDeptTree(sysDept.getId(), sysDeptList));
                    deptTreeList.add(deptTreeVO);
                });
        return deptTreeList;
    }
}
