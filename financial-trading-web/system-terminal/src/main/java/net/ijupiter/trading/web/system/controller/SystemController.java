package net.ijupiter.trading.web.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.system.api.dto.*;
import net.ijupiter.trading.system.api.services.*;
import net.ijupiter.trading.web.common.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统管理控制器
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Slf4j
@Controller
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController extends BaseController {

    private final SystemConfigService systemConfigService;
    private final OperatorService operatorService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final DataDictService dataDictService;

    /**
     * 系统配置页面
     */
    @GetMapping("/config")
    @PreAuthorize("hasAuthority('system:config:view')")
    public String configPage(Model model) {
        model.addAttribute("title", "系统参数管理");
        model.addAttribute("sidebarItems", getSidebarItems("system:config"));
        return "system/config";
    }

    /**
     * 查询系统配置列表
     */
    @GetMapping("/config/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:config:view')")
    public List<SystemConfigDTO> configList(SystemConfigQueryDTO queryDTO) {
        return systemConfigService.querySystemConfigs(queryDTO);
    }

    /**
     * 获取系统配置详情
     */
    @GetMapping("/config/{configId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:config:view')")
    public SystemConfigDTO getConfig(@PathVariable String configId) {
        return systemConfigService.getSystemConfigById(configId);
    }

    /**
     * 新增系统配置
     */
    @PostMapping("/config")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:config:add')")
    public SystemConfigDTO addConfig(@RequestBody SystemConfigDTO configDTO) {
        return systemConfigService.createSystemConfig(configDTO);
    }

    /**
     * 更新系统配置
     */
    @PutMapping("/config")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:config:edit')")
    public SystemConfigDTO updateConfig(@RequestBody SystemConfigDTO configDTO) {
        return systemConfigService.updateSystemConfig(configDTO);
    }

    /**
     * 删除系统配置
     */
    @DeleteMapping("/config/{configId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:config:delete')")
    public Boolean deleteConfig(@PathVariable String configId) {
        return systemConfigService.deleteSystemConfig(configId);
    }

    /**
     * 批量删除系统配置
     */
    @DeleteMapping("/config/batch")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:config:delete')")
    public Integer batchDeleteConfig(@RequestBody List<String> configIds) {
        return systemConfigService.batchDeleteSystemConfigs(configIds);
    }

    /**
     * 操作员管理页面
     */
    @GetMapping("/operator")
    @PreAuthorize("hasAuthority('system:operator:view')")
    public String operatorPage(Model model) {
        model.addAttribute("title", "操作员管理");
        model.addAttribute("sidebarItems", getSidebarItems("system:operator"));
        return "system/operator";
    }

    /**
     * 查询操作员列表
     */
    @GetMapping("/operator/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:operator:view')")
    public List<OperatorDTO> operatorList(OperatorQueryDTO queryDTO) {
        return operatorService.queryOperators(queryDTO);
    }

    /**
     * 获取操作员详情
     */
    @GetMapping("/operator/{operatorId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:operator:view')")
    public OperatorDTO getOperator(@PathVariable String operatorId) {
        return operatorService.getOperatorById(operatorId);
    }

    /**
     * 新增操作员
     */
    @PostMapping("/operator")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:operator:add')")
    public OperatorDTO addOperator(@RequestBody OperatorDTO operatorDTO) {
        return operatorService.createOperator(operatorDTO);
    }

    /**
     * 更新操作员
     */
    @PutMapping("/operator")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:operator:edit')")
    public OperatorDTO updateOperator(@RequestBody OperatorDTO operatorDTO) {
        return operatorService.updateOperator(operatorDTO);
    }

    /**
     * 删除操作员
     */
    @DeleteMapping("/operator/{operatorId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:operator:delete')")
    public Boolean deleteOperator(@PathVariable String operatorId) {
        return operatorService.deleteOperator(operatorId);
    }

    /**
     * 批量删除操作员
     */
    @DeleteMapping("/operator/batch")
    @ResponseBody
    @PreAuthorize("hasAuthority('system:operator:delete')")
    public Integer batchDeleteOperator(@RequestBody List<String> operatorIds) {
        return operatorService.batchDeleteOperators(operatorIds);
    }

    /**
     * 角色管理页面
     */
    @GetMapping("/role")
    @PreAuthorize("hasAuthority('system:role:view')")
    public String rolePage(Model model) {
        model.addAttribute("title", "角色管理");
        model.addAttribute("sidebarItems", getSidebarItems("system:role"));
        return "system/role";
    }

    /**
     * 角色设置页面
     */
    @GetMapping("/permission")
    @PreAuthorize("hasAuthority('system:permission:view')")
    public String permissionPage(Model model) {
        model.addAttribute("title", "权限设置");
        model.addAttribute("sidebarItems", getSidebarItems("system:permission"));
        return "system/permission";
    }

    /**
     * 数据字典页面
     */
    @GetMapping("/dict")
    @PreAuthorize("hasAuthority('system:dict:view')")
    public String dictPage(Model model) {
        model.addAttribute("title", "数据字典管理");
        model.addAttribute("sidebarItems", getSidebarItems("system:dict"));
        return "system/dict";
    }

    /**
     * 构建侧边栏菜单项
     */
    private List<SidebarItem> getSidebarItems(String activeKey) {
        return Arrays.asList(
            new SidebarItem("系统参数", "/system/config", "bi bi-gear-fill", activeKey.equals("system:config")),
            new SidebarItem("操作员管理", "/system/operator", "bi bi-people-fill", activeKey.equals("system:operator")),
            new SidebarItem("角色管理", "/system/role", "bi bi-shield-check", activeKey.equals("system:role")),
            new SidebarItem("权限设置", "/system/permission", "bi bi-key-fill", activeKey.equals("system:permission")),
            new SidebarItem("数据字典", "/system/dict", "bi bi-book", activeKey.equals("system:dict"))
        );
    }

    /**
     * 侧边栏菜单项
     */
    private static class SidebarItem {
        private final String name;
        private final String url;
        private final String icon;
        private final boolean active;

        public SidebarItem(String name, String url, String icon, boolean active) {
            this.name = name;
            this.url = url;
            this.icon = icon;
            this.active = active;
        }

        public String getName() { return name; }
        public String getUrl() { return url; }
        public String getIcon() { return icon; }
        public boolean isActive() { return active; }
    }
}