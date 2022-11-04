package me.cloud.pi.admin.converter;


import me.cloud.pi.admin.pojo.po.SysDept;
import me.cloud.pi.admin.pojo.vo.DeptTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {
    /**
     * po -> vo
     * @param sysDept /
     * @return /
     */
    @Mapping(target = "children", ignore = true)
    DeptTreeVO sysDeptPoToDeptTreeVo(SysDept sysDept);
}
