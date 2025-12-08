package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.web.common.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统模块基础控制器
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

    /**
     * 系统管理首页
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("system/index");
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
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
        
        Map<String, Object> userAdd = new HashMap<>();
        userAdd.put("id", "user:add");
        userAdd.put("name", "添加用户");
        userAdd.put("url", "/system/user/add");
        userAdd.put("active", false);
        userItems.add(userAdd);
        
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