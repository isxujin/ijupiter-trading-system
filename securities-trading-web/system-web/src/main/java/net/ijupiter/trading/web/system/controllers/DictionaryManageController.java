package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.DictionaryDTO;
import net.ijupiter.trading.api.system.dtos.DictionaryItemDTO;
import net.ijupiter.trading.api.system.services.DictionaryService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.dtos.ApiResponse;
import net.ijupiter.trading.web.common.models.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/system/dictionary")
public class DictionaryManageController extends BaseController {
    
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 字典列表页面
     */
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("system/dictionary/list");
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 获取字典列表数据
     */
    @GetMapping("/data")
    @ResponseBody
    public PageResult<DictionaryDTO> getDictionaryList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) String dictCode,
            @RequestParam(required = false) String status) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (dictName != null && !dictName.trim().isEmpty()) {
                searchParams.put("dictName", dictName);
            }
            if (dictCode != null && !dictCode.trim().isEmpty()) {
                searchParams.put("dictCode", dictCode);
            }
            if (status != null && !status.trim().isEmpty()) {
                searchParams.put("status", status);
            }
            
            // 使用基础服务方法获取所有数据
            List<DictionaryDTO> allDictionaries = dictionaryService.findAll();
            // 简单实现过滤功能
            if (dictName != null && !dictName.trim().isEmpty()) {
                allDictionaries = allDictionaries.stream()
                    .filter(d -> d.getDictName().contains(dictName))
                    .collect(java.util.stream.Collectors.toList());
            }
            if (dictCode != null && !dictCode.trim().isEmpty()) {
                allDictionaries = allDictionaries.stream()
                    .filter(d -> dictCode.equals(d.getDictCode()))
                    .collect(java.util.stream.Collectors.toList());
            }
            if (status != null && !status.trim().isEmpty()) {
                allDictionaries = allDictionaries.stream()
                    .filter(d -> status.equals(d.getStatus()))
                    .collect(java.util.stream.Collectors.toList());
            }
            // 模拟分页数据
            int total = allDictionaries.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<DictionaryDTO> pageData = allDictionaries.subList(fromIndex, toIndex);
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取字典列表失败", e);
            return PageResult.failPage("获取字典列表失败");
        }
    }

    /**
     * 添加字典页面
     */
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("system/dictionary/add");
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 编辑字典页面
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("system/dictionary/edit");
        
        try {
            DictionaryDTO dictionary = dictionaryService.findById(id).orElseThrow(() -> new RuntimeException("字典不存在"));
            modelAndView.addObject("dictionary", dictionary);
            
            // 获取字典项列表
            List<DictionaryItemDTO> items = dictionaryService.findItemsByDictionaryId(id);
            modelAndView.addObject("items", items);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            modelAndView.addObject("error", "获取字典信息失败");
        }
        
        // 设置侧边栏菜单
        modelAndView.addObject("sidebarItems", getSystemSidebarItems());
        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 保存字典
     */
    @PostMapping("/save")
    @ResponseBody
    public ApiResponse<DictionaryDTO> saveDictionary(@RequestBody DictionaryDTO dictionaryDTO) {
        try {
            DictionaryDTO savedDictionary = dictionaryService.save(dictionaryDTO);
            return ApiResponse.success("字典保存成功", savedDictionary);
        } catch (Exception e) {
            log.error("保存字典失败", e);
            return ApiResponse.error("保存字典失败");
        }
    }

    /**
     * 更新字典
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public ApiResponse<DictionaryDTO> updateDictionary(@PathVariable Long id, @RequestBody DictionaryDTO dictionaryDTO) {
        try {
            dictionaryDTO.setId(id);
            DictionaryDTO updatedDictionary = dictionaryService.updateDictionary(dictionaryDTO);
            return ApiResponse.success("字典更新成功", updatedDictionary);
        } catch (Exception e) {
            log.error("更新字典失败", e);
            return ApiResponse.error("更新字典失败");
        }
    }

    /**
     * 删除字典
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ApiResponse<Void> deleteDictionary(@PathVariable Long id) {
        try {
            dictionaryService.deleteById(id);
            return ApiResponse.success("字典删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除字典失败", e);
            return ApiResponse.error("删除字典失败");
        }
    }

    /**
     * 批量删除字典
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public ApiResponse<Void> batchDeleteDictionaries(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                dictionaryService.deleteById(id);
            }
            return ApiResponse.success("批量删除字典成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除字典失败", e);
            return ApiResponse.error("批量删除字典失败");
        }
    }

    /**
     * 启用/禁用字典
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public ApiResponse<Void> updateDictionaryStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            DictionaryDTO dictionary = dictionaryService.findById(id).orElseThrow(() -> new RuntimeException("字典不存在"));
            dictionary.setStatus(status);
            dictionaryService.updateDictionary(dictionary);
            return ApiResponse.success("字典状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新字典状态失败", e);
            return ApiResponse.error("更新字典状态失败");
        }
    }

    /**
     * 检查字典名是否存在
     */
    @GetMapping("/check/dictname")
    @ResponseBody
    public ApiResponse<Boolean> checkDictNameExists(@RequestParam String dictName) {
        try {
            boolean exists = dictionaryService.existsByDictName(dictName);
            return ApiResponse.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查字典名失败", e);
            return ApiResponse.error("检查字典名失败");
        }
    }

    /**
     * 检查字典代码是否存在
     */
    @GetMapping("/check/dictcode")
    @ResponseBody
    public ApiResponse<Boolean> checkDictCodeExists(@RequestParam String dictCode) {
        try {
            boolean exists = dictionaryService.existsByDictCode(dictCode);
            return ApiResponse.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查字典代码失败", e);
            return ApiResponse.error("检查字典代码失败");
        }
    }

    /**
     * 获取字典项列表
     */
    @GetMapping("/{dictId}/items")
    @ResponseBody
    public ApiResponse<List<DictionaryItemDTO>> getDictionaryItems(@PathVariable Long dictId) {
        try {
            List<DictionaryItemDTO> items = dictionaryService.findItemsByDictionaryId(dictId);
            return ApiResponse.success("获取字典项成功", items);
        } catch (Exception e) {
            log.error("获取字典项失败", e);
            return ApiResponse.error("获取字典项失败");
        }
    }

    /**
     * 添加字典项
     */
    @PostMapping("/{dictId}/items")
    @ResponseBody
    public ApiResponse<DictionaryItemDTO> addDictionaryItem(@PathVariable Long dictId, @RequestBody DictionaryItemDTO itemDTO) {
        try {
            itemDTO.setDictionaryId(dictId);
            // 使用DictionaryService的createDictionaryItem方法
            DictionaryItemDTO savedItem = dictionaryService.createDictionaryItem(itemDTO);
            return ApiResponse.success("字典项添加成功", savedItem);
        } catch (Exception e) {
            log.error("添加字典项失败", e);
            return ApiResponse.<DictionaryItemDTO>error("添加字典项失败");
        }
    }

    /**
     * 更新字典项
     */
    @PutMapping("/items/{itemId}")
    @ResponseBody
    public ApiResponse<DictionaryItemDTO> updateDictionaryItem(@PathVariable Long itemId, @RequestBody DictionaryItemDTO itemDTO) {
        try {
            itemDTO.setId(itemId);
            DictionaryItemDTO updatedItem = dictionaryService.updateDictionaryItem(itemDTO);
            return ApiResponse.success("字典项更新成功", updatedItem);
        } catch (Exception e) {
            log.error("更新字典项失败", e);
            return ApiResponse.<DictionaryItemDTO>error("更新字典项失败");
        }
    }

    /**
     * 删除字典项
     */
    @DeleteMapping("/items/{itemId}")
    @ResponseBody
    public ApiResponse<Void> deleteDictionaryItem(@PathVariable Long itemId) {
        try {
            dictionaryService.deleteDictionaryItem(itemId);
            return ApiResponse.success("字典项删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除字典项失败", e);
            return ApiResponse.error("删除字典项失败");
        }
    }

    /**
     * 根据字典代码获取字典项
     */
    @GetMapping("/by-code/{dictCode}")
    @ResponseBody
    public ApiResponse<List<DictionaryItemDTO>> getItemsByDictCode(@PathVariable String dictCode) {
        try {
            List<DictionaryItemDTO> items = dictionaryService.findItemsByDictCode(dictCode);
            return ApiResponse.success("获取字典项成功", items);
        } catch (Exception e) {
            log.error("获取字典项失败", e);
            return ApiResponse.error("获取字典项失败");
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
        dictList.put("active", true);
        dictItems.add(dictList);
        
        dictMenu.put("items", dictItems);
        sidebarItems.add(dictMenu);
        
        return sidebarItems;
    }
}