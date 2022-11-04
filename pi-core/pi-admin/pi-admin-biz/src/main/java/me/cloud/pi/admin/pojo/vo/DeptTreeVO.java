package me.cloud.pi.admin.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@Data
public class DeptTreeVO {
    private Long id;
    private String name;
    private List<DeptTreeVO> children;
}
