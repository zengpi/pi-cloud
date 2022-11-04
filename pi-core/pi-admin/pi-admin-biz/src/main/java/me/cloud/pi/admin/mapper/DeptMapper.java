package me.cloud.pi.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cloud.pi.admin.pojo.po.SysDept;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
public interface DeptMapper extends BaseMapper<SysDept> {
    /**
     * 根据部门 ID 判断是否存在
     * @param deptId 部门 ID
     * @return 1：存在；0：不存在
     */
    Integer exists(@Param("deptId") Long deptId);
}
