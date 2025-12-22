package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;
import net.ijupiter.trading.api.system.services.PermissionService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.models.Result;
import net.ijupiter.trading.web.common.models.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/system/permission")
public class PermissionManageController extends BaseController {
    
    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表页面
     */
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("system/permission/list");

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 获取权限列表数据（树形结构）
     */
    @GetMapping("/tree")
    @ResponseBody
    public Result<List<PermissionDTO>> getPermissionTree(
            @RequestParam(required = false) String permissionName,
            @RequestParam(required = false) String permissionType) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (permissionName != null && !permissionName.trim().isEmpty()) {
                searchParams.put("permissionName", permissionName);
            }
            if (permissionType != null && !permissionType.trim().isEmpty()) {
                searchParams.put("permissionType", permissionType);
            }
            
            // 获取所有权限
            List<PermissionDTO> allPermissions = permissionService.findAll();
            
            return Result.success("获取权限树成功", allPermissions);
        } catch (Exception e) {
            log.error("获取权限树失败", e);
            return Result.fail("获取权限树失败");
        }
    }

    /**
     * 获取权限列表数据（分页）
     */
    @GetMapping("/data")
    @ResponseBody
    public PageResult<PermissionDTO> getPermissionList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String permissionName,
            @RequestParam(required = false) String permissionType) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (permissionName != null && !permissionName.trim().isEmpty()) {
                searchParams.put("permissionName", permissionName);
            }
            if (permissionType != null && !permissionType.trim().isEmpty()) {
                searchParams.put("permissionType", permissionType);
            }
            
            // 使用基础服务方法获取所有数据
            List<PermissionDTO> allPermissions = permissionService.findAll();
            // 简单实现过滤功能
            if (permissionName != null && !permissionName.trim().isEmpty()) {
                allPermissions = allPermissions.stream()
                    .filter(p -> p.getPermissionName().contains(permissionName))
                    .collect(java.util.stream.Collectors.toList());
            }
            if (permissionType != null && !permissionType.trim().isEmpty()) {
                allPermissions = allPermissions.stream()
                    .filter(p -> permissionType.equals(p.getPermissionType()))
                    .collect(java.util.stream.Collectors.toList());
            }
            // 模拟分页数据
            int total = allPermissions.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<PermissionDTO> pageData = allPermissions.subList(fromIndex, toIndex);
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取权限列表失败", e);
            return PageResult.failPage("获取权限列表失败");
        }
    }

    /**
     * 添加权限页面
     */
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("system/permission/add");
        
        try {
            // 获取所有权限（用于父权限选择）
            List<PermissionDTO> permissions = permissionService.findAll();
            modelAndView.addObject("permissions", permissions);
        } catch (Exception e) {
            log.error("获取权限列表失败", e);
        }

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 编辑权限页面
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("system/permission/edit");
        
        try {
            PermissionDTO permission = permissionService.findById(id).orElseThrow(() -> new RuntimeException("权限不存在"));
            modelAndView.addObject("permission", permission);
            
            // 获取所有权限（用于父权限选择）
            List<PermissionDTO> permissions = permissionService.findAll();
            modelAndView.addObject("permissions", permissions);
        } catch (Exception e) {
            log.error("获取权限信息失败", e);
            modelAndView.addObject("error", "获取权限信息失败");
        }

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 保存权限
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<PermissionDTO> savePermission(@RequestBody PermissionDTO permissionDTO) {
        try {
            PermissionDTO savedPermission = permissionService.save(permissionDTO);
            return Result.success("权限保存成功", savedPermission);
        } catch (Exception e) {
            log.error("保存权限失败", e);
            return Result.fail("保存权限失败");
        }
    }

    /**
     * 更新权限
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Result<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        try {
            permissionDTO.setId(id);
            PermissionDTO updatedPermission = permissionService.updatePermission(permissionDTO);
            return Result.success("权限更新成功", updatedPermission);
        } catch (Exception e) {
            log.error("更新权限失败", e);
            return Result.fail("更新权限失败");
        }
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> deletePermission(@PathVariable Long id) {
        try {
            permissionService.deleteById(id);
            return Result.success("权限删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除权限失败", e);
            return Result.fail("删除权限失败");
        }
    }

    /**
     * 批量删除权限
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public Result<Void> batchDeletePermissions(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                permissionService.deleteById(id);
            }
            return Result.success("批量删除权限成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除权限失败", e);
            return Result.fail("批量删除权限失败");
        }
    }

    /**
     * 启用/禁用权限
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public Result<Void> updatePermissionStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            PermissionDTO permission = permissionService.findById(id).orElseThrow(() -> new RuntimeException("权限不存在"));
            permission.setStatus(status);
            permissionService.updatePermission(permission);
            return Result.success("权限状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新权限状态失败", e);
            return Result.fail("更新权限状态失败");
        }
    }

    /**
     * 检查权限名是否存在
     */
    @GetMapping("/check/permissionname")
    @ResponseBody
    public Result<Boolean> checkPermissionNameExists(@RequestParam String permissionName) {
        try {
            boolean exists = permissionService.existsByPermissionName(permissionName);
            return Result.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查权限名失败", e);
            return Result.fail("检查权限名失败");
        }
    }

    /**
     * 检查权限代码是否存在
     */
    @GetMapping("/check/permissioncode")
    @ResponseBody
    public Result<Boolean> checkPermissionCodeExists(@RequestParam String permissionCode) {
        try {
            boolean exists = permissionService.existsByPermissionCode(permissionCode);
            return Result.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查权限代码失败", e);
            return Result.fail("检查权限代码失败");
        }
    }

    /**
     * 获取子权限
     */
    @GetMapping("/children/{parentId}")
    @ResponseBody
    public Result<List<PermissionDTO>> getChildPermissions(@PathVariable Long parentId) {
        try {
            List<PermissionDTO> children = permissionService.findByParentId(parentId);
            return Result.success("获取子权限成功", children);
        } catch (Exception e) {
            log.error("获取子权限失败", e);
            return Result.fail("获取子权限失败");
        }
    }
}