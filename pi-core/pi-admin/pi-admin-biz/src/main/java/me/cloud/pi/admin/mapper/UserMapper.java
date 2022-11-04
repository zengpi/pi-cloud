package me.cloud.pi.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.cloud.pi.admin.pojo.po.SysUser;
import me.cloud.pi.admin.pojo.query.UserQueryParam;
import me.cloud.pi.admin.pojo.vo.OptionalUserVO;
import me.cloud.pi.admin.pojo.vo.UserExportVO;
import me.cloud.pi.admin.pojo.vo.UserVO;
import me.cloud.pi.common.mybatis.util.PiPage;
import me.cloud.pi.common.web.pojo.query.BaseQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-19
 */
public interface UserMapper extends BaseMapper<SysUser> {
    /**
     * 用户查询
     *
     * @param userQuery 查询条件
     * @param page 分页
     * @return /
     */
    PiPage<UserVO> user(@Param("page") IPage<?> page, @Param("userQuery") UserQueryParam userQuery);

    /**
     * 获取下载记录
     * @param page 分页
     * @param query 查询条件
     * @return 下载记录
     */
    List<UserExportVO> listDownloadRecode(@Param("page") PiPage<UserExportVO> page, @Param("query") UserQueryParam query);

    /**
     * 指定的用户名是否存在
     * @param username 用户名
     * @return 如果用户名存在，则返回 1，否则返回 0
     */
    Integer userNameExists(@Param("username") String username);

    /**
     * 可选用户
     *
     * @param query 查询条件
     * @return 可选用户列表
     */
    PiPage<OptionalUserVO> getOptionalUsers(PiPage<OptionalUserVO> objectPiPage, BaseQueryParam query);
}
