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

package me.pi.cloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.pi.cloud.admin.converter.DeptConverter;
import me.pi.cloud.admin.mapper.DeptMapper;
import me.pi.cloud.admin.pojo.dto.DeptDTO;
import me.pi.cloud.admin.pojo.po.SysDept;
import me.pi.cloud.admin.pojo.query.DeptTreeQuery;
import me.pi.cloud.admin.pojo.vo.DeptTreeVO;
import me.pi.cloud.admin.service.DeptService;
import me.pi.cloud.common.web.constant.PiConstants;
import me.pi.cloud.common.web.exception.BadRequestException;
import me.pi.cloud.common.web.vo.SelectTreeVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, SysDept> implements DeptService {
    private final DeptConverter deptConverter;
    private final DeptMapper deptMapper;

    @Override
    public List<DeptTreeVO> getDeptTree(DeptTreeQuery query) {
        List<SysDept> sysDeptList = super.list(Wrappers.lambdaQuery(SysDept.class)
                .like(StrUtil.isNotBlank(query.getKeyWord()), SysDept::getName, query.getKeyWord())
                .orderByAsc(SysDept::getSort)
                .select(SysDept::getId, SysDept::getCreateTime, SysDept::getName, SysDept::getSort,
                        SysDept::getParentId, SysDept::getParentId));

        if (CollUtil.isEmpty(sysDeptList)) {
            return Collections.emptyList();
        }

        if (StrUtil.isBlank(query.getKeyWord())) {
            return buildDeptTree(PiConstants.TREE_ROOT_ID, sysDeptList);
        }

        Set<Long> deptIdSet = sysDeptList.stream().map(SysDept::getId).collect(Collectors.toSet());

        return sysDeptList.stream().map(sysDept -> {
            if (!deptIdSet.contains(sysDept.getParentId())) {
                deptIdSet.add(sysDept.getParentId());
                return buildDeptTree(sysDept.getParentId(), sysDeptList);
            } else {
                return new ArrayList<DeptTreeVO>();
            }
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    @Override
    public void saveOrUpdate(DeptDTO dto) {
        if (dto.getParentId().equals(dto.getId())) {
            throw new BadRequestException("上级类目不能为自己");
        }
        SysDept sysDept = deptConverter.deptDtoToSysDept(dto);
        super.saveOrUpdate(sysDept);
    }

    @Override
    public void deleteDept(String ids) {
        Set<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        Set<Long> toBeDeletedIdSet = new HashSet<>();
        idSet.forEach(id -> {
            if (!toBeDeletedIdSet.contains(id)) {
                toBeDeletedIdSet.add(id);
                getToBeDeletedChildrenIds(id, toBeDeletedIdSet);
            }
        });
        super.removeByIds(toBeDeletedIdSet);
    }

    @Override
    public List<SelectTreeVO> getDeptSelectTree() {
        List<SysDept> sysDeptList = super.list(Wrappers.lambdaQuery(SysDept.class)
                .select(SysDept::getId, SysDept::getName, SysDept::getParentId));
        return buildDeptSelectTree(0L, sysDeptList);
    }

    @Override
    public Integer existsByDeptId(Long deptId) {
        return deptMapper.existsByDeptId(deptId);
    }

    private List<DeptTreeVO> buildDeptTree(Long parentId, List<SysDept> sysDeptList) {
        return sysDeptList.stream()
                .filter(sysDept -> sysDept.getParentId().equals(parentId))
                .map(sysDept -> {
                    DeptTreeVO deptTreeVO = deptConverter.sysDeptToDeptTreeVO(sysDept);
                    deptTreeVO.setChildren(buildDeptTree(sysDept.getId(), sysDeptList));
                    return deptTreeVO;
                }).collect(Collectors.toList());
    }

    private void getToBeDeletedChildrenIds(Long parentId, Set<Long> toBeDeletedIdSet) {
        List<SysDept> childrenDeptList = super.list(Wrappers.lambdaQuery(SysDept.class)
                .eq(SysDept::getParentId, parentId)
                .select(SysDept::getId));

        childrenDeptList.forEach(childrenDept -> {
            toBeDeletedIdSet.add(childrenDept.getId());
            getToBeDeletedChildrenIds(childrenDept.getId(), toBeDeletedIdSet);
        });
    }

    private List<SelectTreeVO> buildDeptSelectTree(Long parentId, List<SysDept> sysDeptList) {
        return sysDeptList.stream()
                .filter(sysDept -> sysDept.getParentId().equals(parentId))
                .map(sysDept -> {
                    SelectTreeVO deptSelectTreeVO = deptConverter.sysDeptToSelectTreeVo(sysDept);
                    deptSelectTreeVO.setChildren(buildDeptSelectTree(sysDept.getId(), sysDeptList));
                    return deptSelectTreeVO;
                }).collect(Collectors.toList());
    }
}
