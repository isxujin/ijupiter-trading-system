package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.DictionaryDTO;
import net.ijupiter.trading.api.system.dtos.DictionaryItemDTO;
import net.ijupiter.trading.api.system.services.DictionaryService;
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

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 保存字典
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<DictionaryDTO> saveDictionary(@RequestBody DictionaryDTO dictionaryDTO) {
        try {
            DictionaryDTO savedDictionary = dictionaryService.save(dictionaryDTO);
            return Result.success("字典保存成功", savedDictionary);
        } catch (Exception e) {
            log.error("保存字典失败", e);
            return Result.fail("保存字典失败");
        }
    }

    /**
     * 更新字典
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Result<DictionaryDTO> updateDictionary(@PathVariable Long id, @RequestBody DictionaryDTO dictionaryDTO) {
        try {
            dictionaryDTO.setId(id);
            DictionaryDTO updatedDictionary = dictionaryService.updateDictionary(dictionaryDTO);
            return Result.success("字典更新成功", updatedDictionary);
        } catch (Exception e) {
            log.error("更新字典失败", e);
            return Result.fail("更新字典失败");
        }
    }

    /**
     * 删除字典
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> deleteDictionary(@PathVariable Long id) {
        try {
            dictionaryService.deleteById(id);
            return Result.success("字典删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除字典失败", e);
            return Result.fail("删除字典失败");
        }
    }

    /**
     * 批量删除字典
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public Result<Void> batchDeleteDictionaries(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                dictionaryService.deleteById(id);
            }
            return Result.success("批量删除字典成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除字典失败", e);
            return Result.fail("批量删除字典失败");
        }
    }

    /**
     * 启用/禁用字典
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public Result<Void> updateDictionaryStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            DictionaryDTO dictionary = dictionaryService.findById(id).orElseThrow(() -> new RuntimeException("字典不存在"));
            dictionary.setStatus(status);
            dictionaryService.updateDictionary(dictionary);
            return Result.success("字典状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新字典状态失败", e);
            return Result.fail("更新字典状态失败");
        }
    }

    /**
     * 检查字典名是否存在
     */
    @GetMapping("/check/dictname")
    @ResponseBody
    public Result<Boolean> checkDictNameExists(@RequestParam String dictName) {
        try {
            boolean exists = dictionaryService.existsByDictName(dictName);
            return Result.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查字典名失败", e);
            return Result.fail("检查字典名失败");
        }
    }

    /**
     * 检查字典代码是否存在
     */
    @GetMapping("/check/dictcode")
    @ResponseBody
    public Result<Boolean> checkDictCodeExists(@RequestParam String dictCode) {
        try {
            boolean exists = dictionaryService.existsByDictCode(dictCode);
            return Result.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查字典代码失败", e);
            return Result.fail("检查字典代码失败");
        }
    }

    /**
     * 获取字典项列表
     */
    @GetMapping("/{dictId}/items")
    @ResponseBody
    public Result<List<DictionaryItemDTO>> getDictionaryItems(@PathVariable Long dictId) {
        try {
            List<DictionaryItemDTO> items = dictionaryService.findItemsByDictionaryId(dictId);
            return Result.success("获取字典项成功", items);
        } catch (Exception e) {
            log.error("获取字典项失败", e);
            return Result.fail("获取字典项失败");
        }
    }

    /**
     * 添加字典项
     */
    @PostMapping("/{dictId}/items")
    @ResponseBody
    public Result<DictionaryItemDTO> addDictionaryItem(@PathVariable Long dictId, @RequestBody DictionaryItemDTO itemDTO) {
        try {
            itemDTO.setDictionaryId(dictId);
            // 使用DictionaryService的createDictionaryItem方法
            DictionaryItemDTO savedItem = dictionaryService.createDictionaryItem(itemDTO);
            return Result.success("字典项添加成功", savedItem);
        } catch (Exception e) {
            log.error("添加字典项失败", e);
            return Result.<DictionaryItemDTO>fail("添加字典项失败");
        }
    }

    /**
     * 更新字典项
     */
    @PutMapping("/items/{itemId}")
    @ResponseBody
    public Result<DictionaryItemDTO> updateDictionaryItem(@PathVariable Long itemId, @RequestBody DictionaryItemDTO itemDTO) {
        try {
            itemDTO.setId(itemId);
            DictionaryItemDTO updatedItem = dictionaryService.updateDictionaryItem(itemDTO);
            return Result.success("字典项更新成功", updatedItem);
        } catch (Exception e) {
            log.error("更新字典项失败", e);
            return Result.<DictionaryItemDTO>fail("更新字典项失败");
        }
    }

    /**
     * 删除字典项
     */
    @DeleteMapping("/items/{itemId}")
    @ResponseBody
    public Result<Void> deleteDictionaryItem(@PathVariable Long itemId) {
        try {
            dictionaryService.deleteDictionaryItem(itemId);
            return Result.success("字典项删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除字典项失败", e);
            return Result.fail("删除字典项失败");
        }
    }

    /**
     * 根据字典代码获取字典项
     */
    @GetMapping("/by-code/{dictCode}")
    @ResponseBody
    public Result<List<DictionaryItemDTO>> getItemsByDictCode(@PathVariable String dictCode) {
        try {
            List<DictionaryItemDTO> items = dictionaryService.findItemsByDictCode(dictCode);
            return Result.success("获取字典项成功", items);
        } catch (Exception e) {
            log.error("获取字典项失败", e);
            return Result.fail("获取字典项失败");
        }
    }

}