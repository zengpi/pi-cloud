package me.cloud.pi.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.cloud.pi.admin.pojo.po.SysDept;
import me.cloud.pi.admin.pojo.vo.DeptTreeVO;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
public interface DeptService extends IService<SysDept> {
    /**
     * 获取部门树
     * @return 部门树
     */
    List<DeptTreeVO> getDeptTree();
}
