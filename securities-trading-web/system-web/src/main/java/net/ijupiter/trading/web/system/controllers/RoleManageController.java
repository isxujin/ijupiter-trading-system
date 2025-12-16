package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.RoleDTO;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;
import net.ijupiter.trading.api.system.services.RoleService;
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
import java.util.Optional;
import java.util.Optional;

/**
 * 角色管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/system/role")
public class RoleManageController extends BaseController {
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PermissionService permissionService;

    /**
     * 角色列表页面
     */
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("system/role/list");
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 获取角色列表数据
     */
    @GetMapping("/data")
    @ResponseBody
    public PageResult<RoleDTO> getRoleList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String status) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (roleName != null && !roleName.trim().isEmpty()) {
                searchParams.put("roleName", roleName);
            }
            if (status != null && !status.trim().isEmpty()) {
                searchParams.put("status", status);
            }
            
            // 使用基础服务方法获取所有数据
            List<RoleDTO> allRoles = roleService.findAll();
            // 模拟分页数据
            int total = allRoles.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<RoleDTO> pageData = allRoles.subList(fromIndex, toIndex);
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取角色列表失败", e);
            return PageResult.failPage("获取角色列表失败");
        }
    }

    /**
     * 添加角色页面
     */
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("system/role/add");
        
        try {
            // 获取所有权限列表
            Map<String, Object> searchParams = new HashMap<>();
            // 获取所有权限
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
     * 编辑角色页面
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("system/role/edit");
        
        try {
            RoleDTO role = roleService.findById(id).orElseThrow(() -> new RuntimeException("角色不存在"));
            modelAndView.addObject("role", role);
            
            // 获取所有权限列表
            Map<String, Object> searchParams = new HashMap<>();
            // 获取所有权限
            List<PermissionDTO> permissions = permissionService.findAll();
            modelAndView.addObject("permissions", permissions);
            
            // 获取角色已有的权限
            // 假设有一个方法可以获取角色权限
            List<PermissionDTO> rolePermissions = new java.util.ArrayList<>();
            // 这里需要实现一个方法来获取角色权限，或者使用其他逻辑
            // 临时设置为空列表
            modelAndView.addObject("rolePermissions", rolePermissions);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            modelAndView.addObject("error", "获取角色信息失败");
        }
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @ResponseBody
    public ApiResponse<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO savedRole = roleService.save(roleDTO);
            return ApiResponse.success("角色保存成功", savedRole);
        } catch (Exception e) {
            log.error("保存角色失败", e);
            return ApiResponse.error("保存角色失败");
        }
    }

    /**
     * 更新角色
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public ApiResponse<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        try {
            roleDTO.setId(id);
            RoleDTO updatedRole = roleService.updateRoleInfo(roleDTO);
            return ApiResponse.success("角色更新成功", updatedRole);
        } catch (Exception e) {
            log.error("更新角色失败", e);
            return ApiResponse.error("更新角色失败");
        }
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ApiResponse<Void> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteById(id);
            return ApiResponse.success("角色删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除角色失败", e);
            return ApiResponse.error("删除角色失败");
        }
    }

    /**
     * 批量删除角色
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public ApiResponse<Void> batchDeleteRoles(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                roleService.deleteById(id);
            }
            return ApiResponse.success("批量删除角色成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除角色失败", e);
            return ApiResponse.error("批量删除角色失败");
        }
    }

    /**
     * 启用/禁用角色
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public ApiResponse<Void> updateRoleStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            RoleDTO role = roleService.findById(id).orElseThrow(() -> new RuntimeException("角色不存在"));
            role.setStatus(status);
            roleService.updateRoleInfo(role);
            return ApiResponse.success("角色状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新角色状态失败", e);
            return ApiResponse.error("更新角色状态失败");
        }
    }

    /**
     * 为角色分配权限
     */
    @PostMapping("/{roleId}/permissions")
    @ResponseBody
    public ApiResponse<Void> assignPermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        try {
            roleService.assignPermissions(roleId, permissionIds);
            return ApiResponse.success("权限分配成功", (Void)null);
        } catch (Exception e) {
            log.error("分配权限失败", e);
            return ApiResponse.error("分配权限失败");
        }
    }

    /**
     * 获取角色权限
     */
    @GetMapping("/{roleId}/permissions")
    @ResponseBody
    public ApiResponse<List<PermissionDTO>> getRolePermissions(@PathVariable Long roleId) {
        try {
            // 需要先根据角色ID查找角色，然后使用权限服务获取权限列表
            Optional<RoleDTO> role = roleService.findById(roleId);
            if (role.isPresent()) {
                List<PermissionDTO> permissions = permissionService.findByRoleId(roleId);
                return ApiResponse.success("获取角色权限成功", permissions);
            } else {
                return ApiResponse.<List<PermissionDTO>>error("角色不存在");
            }
        } catch (Exception e) {
            log.error("获取角色权限失败", e);
            return ApiResponse.<List<PermissionDTO>>error("获取角色权限失败");
        }
    }

    /**
     * 检查角色名是否存在
     */
    @GetMapping("/check/rolename")
    @ResponseBody
    public ApiResponse<Boolean> checkRoleNameExists(@RequestParam String roleName) {
        try {
            boolean exists = roleService.existsByRoleName(roleName);
            return ApiResponse.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查角色名失败", e);
            return ApiResponse.error("检查角色名失败");
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
        roleList.put("active", true);
        roleItems.add(roleList);
        
        Map<String, Object> roleAdd = new HashMap<>();
        roleAdd.put("id", "role:add");
        roleAdd.put("name", "添加角色");
        roleAdd.put("url", "/system/role/add");
        roleAdd.put("active", false);
        roleItems.add(roleAdd);
        
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
        permissionList.put("active", false);
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