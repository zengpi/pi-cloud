package me.cloud.pi.common.web.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-23
 */
@Data
public class SelectTreeVO {
    /**
     * 节点值
     */
    private Long value;
    /**
     * 节点标签
     */
    private String label;
    /**
     * 子树
     */
    List<SelectTreeVO> children;
}
