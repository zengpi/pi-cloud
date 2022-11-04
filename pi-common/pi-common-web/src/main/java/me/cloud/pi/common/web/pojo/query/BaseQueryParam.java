package me.cloud.pi.common.web.pojo.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZnPi
 * @date 2022-08-29
 */
@Getter
@Setter
public class BaseQueryParam {
    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 20;

    /**
     * 关键词
     */
    private String keyWord;
}
