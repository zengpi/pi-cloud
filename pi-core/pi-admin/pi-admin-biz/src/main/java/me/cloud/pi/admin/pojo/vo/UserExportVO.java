package me.cloud.pi.admin.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * 用户导出 VO
 * @author ZnPi
 * @date 2022-09-14
 */
@Data
@ColumnWidth(20)
public class UserExportVO {
    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    private String username;
    /**
     * 昵称
     */
    @ExcelProperty("昵称")
    private String nickname;
    /**
     * 手机
     */
    @ExcelProperty("手机")
    private String phone;
    /**
     * 部门
     */
    @ExcelProperty("部门")
    private String deptName;
}
