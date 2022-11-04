package me.cloud.pi.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.pojo.dto.MenuDTO;
import me.cloud.pi.admin.pojo.query.MenuQueryParam;
import me.cloud.pi.admin.pojo.vo.MenuVO;
import me.cloud.pi.admin.service.MenuService;
import me.cloud.pi.admin.service.RoleMenuService;
import me.cloud.pi.common.web.pojo.vo.SelectTreeVO;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    /**
     * 获取菜单
     *
     * @return 菜单列表
     */
    @GetMapping
    public ResponseData<List<MenuVO>> getMenus(MenuQueryParam query) {
        return ResponseData.ok(menuService.getMenus(query));
    }

    /**
     * 菜单树形选择
     *
     * @param containBtn 是否包含按钮
     * @return /
     */
    @GetMapping("/selectTree")
    public ResponseData<List<SelectTreeVO>> selectTree(boolean containBtn) {
        return ResponseData.ok(menuService.selectTree(containBtn));
    }

    /**
     * 返回当前用户的树形菜单
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping("/buildMenu")
    public ResponseData<List<Tree<Long>>> buildMenu() {
        return ResponseData.ok(menuService.buildMenu());
    }

    /**
     * 新增或编辑
     *
     * @param dto 菜单表单 DTO
     * @return /
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseData<?> saveOrUpdate(@RequestBody MenuDTO dto) {
        menuService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    /**
     * 删除
     * @param ids 菜单 ID，多个以逗号分隔
     * @return /
     */
    @DeleteMapping
    public ResponseData<?> delete(String ids){
        menuService.delete(ids);
        return ResponseData.ok();
    }

    /**
     * 根据角色 ID 获取菜单 ID 列表
     * @param roleId 角色 ID
     * @return 菜单 ID 列表
     */
    @GetMapping("/menuLeafIds/{roleId}")
    public ResponseData<List<Long>> getMenuLeafIdsByRoleId(@PathVariable Long roleId){
        return ResponseData.ok(roleMenuService.getMenuLeafIdsByRoleId(roleId));
    }
}
