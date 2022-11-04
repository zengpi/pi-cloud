package me.cloud.pi.admin.controller;

import lombok.RequiredArgsConstructor;
import me.cloud.pi.admin.pojo.vo.DeptTreeVO;
import me.cloud.pi.admin.service.DeptService;
import me.cloud.pi.common.web.util.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZnPi
 * @date 2022-09-04
 */
@RestController
@RequestMapping("dept")
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @GetMapping("/deptTree")
    public ResponseData<List<DeptTreeVO>> deptTree(){
        return ResponseData.ok(deptService.getDeptTree());
    }
}
