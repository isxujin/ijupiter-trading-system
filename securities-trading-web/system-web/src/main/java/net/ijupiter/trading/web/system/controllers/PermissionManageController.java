package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;
import net.ijupiter.trading.api.system.services.PermissionService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.dtos.ApiResponse;
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
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 获取权限列表数据（树形结构）
     */
    @GetMapping("/tree")
    @ResponseBody
    public ApiResponse<List<PermissionDTO>> getPermissionTree(
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
            
            return ApiResponse.success("获取权限树成功", allPermissions);
        } catch (Exception e) {
            log.error("获取权限树失败", e);
            return ApiResponse.error("获取权限树失败");
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
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
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
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 保存权限
     */
    @PostMapping("/save")
    @ResponseBody
    public ApiResponse<PermissionDTO> savePermission(@RequestBody PermissionDTO permissionDTO) {
        try {
            PermissionDTO savedPermission = permissionService.save(permissionDTO);
            return ApiResponse.success("权限保存成功", savedPermission);
        } catch (Exception e) {
            log.error("保存权限失败", e);
            return ApiResponse.error("保存权限失败");
        }
    }

    /**
     * 更新权限
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public ApiResponse<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        try {
            permissionDTO.setId(id);
            PermissionDTO updatedPermission = permissionService.updatePermission(permissionDTO);
            return ApiResponse.success("权限更新成功", updatedPermission);
        } catch (Exception e) {
            log.error("更新权限失败", e);
            return ApiResponse.error("更新权限失败");
        }
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ApiResponse<Void> deletePermission(@PathVariable Long id) {
        try {
            permissionService.deleteById(id);
            return ApiResponse.success("权限删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除权限失败", e);
            return ApiResponse.error("删除权限失败");
        }
    }

    /**
     * 批量删除权限
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public ApiResponse<Void> batchDeletePermissions(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                permissionService.deleteById(id);
            }
            return ApiResponse.success("批量删除权限成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除权限失败", e);
            return ApiResponse.error("批量删除权限失败");
        }
    }

    /**
     * 启用/禁用权限
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public ApiResponse<Void> updatePermissionStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            PermissionDTO permission = permissionService.findById(id).orElseThrow(() -> new RuntimeException("权限不存在"));
            permission.setStatus(status);
            permissionService.updatePermission(permission);
            return ApiResponse.success("权限状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新权限状态失败", e);
            return ApiResponse.error("更新权限状态失败");
        }
    }

    /**
     * 检查权限名是否存在
     */
    @GetMapping("/check/permissionname")
    @ResponseBody
    public ApiResponse<Boolean> checkPermissionNameExists(@RequestParam String permissionName) {
        try {
            boolean exists = permissionService.existsByPermissionName(permissionName);
            return ApiResponse.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查权限名失败", e);
            return ApiResponse.error("检查权限名失败");
        }
    }

    /**
     * 检查权限代码是否存在
     */
    @GetMapping("/check/permissioncode")
    @ResponseBody
    public ApiResponse<Boolean> checkPermissionCodeExists(@RequestParam String permissionCode) {
        try {
            boolean exists = permissionService.existsByPermissionCode(permissionCode);
            return ApiResponse.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查权限代码失败", e);
            return ApiResponse.error("检查权限代码失败");
        }
    }

    /**
     * 获取子权限
     */
    @GetMapping("/children/{parentId}")
    @ResponseBody
    public ApiResponse<List<PermissionDTO>> getChildPermissions(@PathVariable Long parentId) {
        try {
            List<PermissionDTO> children = permissionService.findByParentId(parentId);
            return ApiResponse.success("获取子权限成功", children);
        } catch (Exception e) {
            log.error("获取子权限失败", e);
            return ApiResponse.error("获取子权限失败");
        }
    }

    /**
     * 获取系统管理侧边栏菜单项
     */
    private java.util.List<Map<String, Object>> getSystemSidebarItems() {
        java.util.List<Map<String, Object>> sidebarItems = getCommonSidebarItems("system");
        
        // 用户管理菜单
        Map<String, Object> userMenu = new HashMap<>();
        userMenu.put("id", "user");
        userMenu.put("name", "用户管理");
        userMenu.put("icon", "bi-people");
        
        java.util.List<Map<String, Object>> userItems = new java.util.ArrayList<>();
        
        Map<String, Object> userList = new HashMap<>();
        userList.put("id", "user:list");
        userList.put("name", "用户列表");
        userList.put("url", "/system/user/list");
        userList.put("active", false);
        userItems.add(userList);
        
        userMenu.put("items", userItems);
        sidebarItems.add(userMenu);
        
        // 角色管理菜单
        Map<String, Object> roleMenu = new HashMap<>();
        roleMenu.put("id", "role");
        roleMenu.put("name", "角色管理");
        roleMenu.put("icon", "bi-shield-check");
        
        java.util.List<Map<String, Object>> roleItems = new java.util.ArrayList<>();
        
        Map<String, Object> roleList = new HashMap<>();
        roleList.put("id", "role:list");
        roleList.put("name", "角色列表");
        roleList.put("url", "/system/role/list");
        roleList.put("active", false);
        roleItems.add(roleList);
        
        roleMenu.put("items", roleItems);
        sidebarItems.add(roleMenu);
        
        // 权限管理菜单
        Map<String, Object> permissionMenu = new HashMap<>();
        permissionMenu.put("id", "permission");
        permissionMenu.put("name", "权限管理");
        permissionMenu.put("icon", "bi-key");
        
        java.util.List<Map<String, Object>> permissionItems = new java.util.ArrayList<>();
        
        Map<String, Object> permissionList = new HashMap<>();
        permissionList.put("id", "permission:list");
        permissionList.put("name", "权限列表");
        permissionList.put("url", "/system/permission/list");
        permissionList.put("active", true);
        permissionItems.add(permissionList);
        
        permissionMenu.put("items", permissionItems);
        sidebarItems.add(permissionMenu);
        
        // 字典管理菜单
        Map<String, Object> dictMenu = new HashMap<>();
        dictMenu.put("id", "dictionary");
        dictMenu.put("name", "字典管理");
        dictMenu.put("icon", "bi-book");
        
        java.util.List<Map<String, Object>> dictItems = new java.util.ArrayList<>();
        
        Map<String, Object> dictList = new HashMap<>();
        dictList.put("id", "dictionary:list");
        dictList.put("name", "字典列表");
        dictList.put("url", "/system/dictionary/list");
        dictList.put("active", false);
        dictItems.add(dictList);
        
        dictMenu.put("items", dictItems);
        sidebarItems.add(dictMenu);
        
        return sidebarItems;
    }
}