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
