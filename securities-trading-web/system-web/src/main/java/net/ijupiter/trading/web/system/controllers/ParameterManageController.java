package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.ParameterDTO;
import net.ijupiter.trading.api.system.services.ParameterService;
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
 * 系统参数管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/system/parameter")
public class ParameterManageController extends BaseController {
    
    @Autowired
    private ParameterService parameterService;

    /**
     * 参数列表页面
     */
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("system/parameter/list");

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 获取参数列表数据
     */
    @GetMapping("/data")
    @ResponseBody
    public PageResult<ParameterDTO> getParameterList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String paramGroup,
            @RequestParam(required = false) String paramType,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        try {
            // 构建查询条件
            Map<String, Object> searchParams = new HashMap<>();
            if (keyword != null && !keyword.trim().isEmpty()) {
                searchParams.put("keyword", keyword.trim());
            }
            if (paramGroup != null && !paramGroup.trim().isEmpty()) {
                searchParams.put("paramGroup", paramGroup.trim());
            }
            if (paramType != null && !paramType.trim().isEmpty()) {
                searchParams.put("paramType", paramType.trim());
            }
            if (status != null) {
                searchParams.put("status", status);
            }
            
            // 由于BaseService的findAll方法不带参数，需要手动分页
            List<ParameterDTO> allParameters;
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                // 使用关键词搜索
                allParameters = parameterService.searchByKeyword(keyword.trim());
            } else if (paramGroup != null && !paramGroup.trim().isEmpty() && status != null) {
                // 按分组和状态查询
                allParameters = parameterService.findByParamGroupAndStatus(paramGroup.trim(), status);
            } else if (paramGroup != null && !paramGroup.trim().isEmpty()) {
                // 按分组查询
                allParameters = parameterService.findByParamGroupOrderBySortOrder(paramGroup.trim());
            } else if (paramType != null && !paramType.trim().isEmpty()) {
                // 按类型查询
                allParameters = parameterService.findByParamType(paramType.trim());
            } else if (status != null) {
                // 按状态查询
                allParameters = parameterService.findByStatus(status);
            } else {
                // 查询所有
                allParameters = parameterService.findAll();
            }
            
            // 手动分页
            int total = allParameters.size();
            int startIndex = (pageNum - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, total);
            
            List<ParameterDTO> pageList;
            if (startIndex >= total) {
                pageList = List.of();
            } else {
                pageList = allParameters.subList(startIndex, endIndex);
            }
            
            return PageResult.success(pageNum, pageSize, total, pageList);
                    
        } catch (Exception e) {
            log.error("获取参数列表失败", e);
            return PageResult.failPage("获取参数列表失败");
        }
    }

    /**
     * 参数详情页面
     */
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("system/parameter/detail");
        
        ParameterDTO parameter = parameterService.findById(id)
                .orElseThrow(() -> new RuntimeException("参数不存在"));

        modelAndView.addObject("activeModule", "system");
        modelAndView.addObject("parameter", parameter);
        
        return modelAndView;
    }

    /**
     * 添加参数页面
     */
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("system/parameter/add");

        modelAndView.addObject("activeModule", "system");
        
        // 获取所有参数分组
        modelAndView.addObject("paramGroups", parameterService.findAllParamGroups());
        
        return modelAndView;
    }

    /**
     * 编辑参数页面
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("system/parameter/edit");
        
        ParameterDTO parameter = parameterService.findById(id)
                .orElseThrow(() -> new RuntimeException("参数不存在"));

        modelAndView.addObject("activeModule", "system");
        modelAndView.addObject("parameter", parameter);
        modelAndView.addObject("paramGroups", parameterService.findAllParamGroups());
        
        return modelAndView;
    }

    /**
     * 保存参数
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<ParameterDTO> saveParameter(@RequestBody ParameterDTO parameterDTO) {
        try {
            ParameterDTO savedParameter = parameterService.createParameter(parameterDTO);
            return Result.success("参数保存成功", savedParameter);
        } catch (Exception e) {
            log.error("保存参数失败", e);
            return Result.<ParameterDTO>fail("保存参数失败");
        }
    }

    /**
     * 更新参数
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Result<ParameterDTO> updateParameter(@PathVariable Long id, @RequestBody ParameterDTO parameterDTO) {
        try {
            parameterDTO.setId(id);
            ParameterDTO updatedParameter = parameterService.updateParameter(parameterDTO);
            return Result.success("参数更新成功", updatedParameter);
        } catch (Exception e) {
            log.error("更新参数失败", e);
            return Result.fail("更新参数失败");
        }
    }

    /**
     * 删除参数
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> deleteParameter(@PathVariable Long id) {
        try {
            parameterService.deleteById(id);
            return Result.success("参数删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除参数失败", e);
            return Result.fail("删除参数失败");
        }
    }

    /**
     * 批量删除参数
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public Result<Void> batchDeleteParameters(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                parameterService.deleteById(id);
            }
            return Result.success("批量删除参数成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除参数失败", e);
            return Result.fail("批量删除参数失败");
        }
    }

    /**
     * 启用/禁用参数
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public Result<Void> updateParameterStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (status == 1) {
                parameterService.enableParameter(id);
            } else {
                parameterService.disableParameter(id);
            }
            return Result.success("参数状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新参数状态失败", e);
            return Result.fail("更新参数状态失败");
        }
    }

    /**
     * 重置参数为默认值
     */
    @PutMapping("/reset/{id}")
    @ResponseBody
    public Result<Void> resetParameter(@PathVariable Long id) {
        try {
            parameterService.resetToDefaultValue(id);
            return Result.success("参数重置成功", (Void)null);
        } catch (Exception e) {
            log.error("重置参数失败", e);
            return Result.fail("重置参数失败");
        }
    }

    /**
     * 获取参数分组
     */
    @GetMapping("/groups")
    @ResponseBody
    public Result<List<String>> getParamGroups() {
        try {
            List<String> groups = parameterService.findAllParamGroups();
            return Result.success("获取参数分组成功", groups);
        } catch (Exception e) {
            log.error("获取参数分组失败", e);
            return Result.<List<String>>fail("获取参数分组失败");
        }
    }

    /**
     * 获取系统参数
     */
    @GetMapping("/system")
    @ResponseBody
    public Result<List<ParameterDTO>> getSystemParameters() {
        try {
            List<ParameterDTO> parameters = parameterService.findSystemParameters();
            return Result.success("获取系统参数成功", parameters);
        } catch (Exception e) {
            log.error("获取系统参数失败", e);
            return Result.<List<ParameterDTO>>fail("获取系统参数失败");
        }
    }

    /**
     * 获取指定分组的参数
     */
    @GetMapping("/group/{paramGroup}")
    @ResponseBody
    public Result<List<ParameterDTO>> getParametersByGroup(@PathVariable String paramGroup) {
        try {
            List<ParameterDTO> parameters = parameterService.findByParamGroupOrderBySortOrder(paramGroup);
            return Result.success("获取参数分组成功", parameters);
        } catch (Exception e) {
            log.error("获取参数分组失败", e);
            return Result.<List<ParameterDTO>>fail("获取参数分组失败");
        }
    }

    /**
     * 检查参数编码是否存在
     */
    @GetMapping("/check/paramcode")
    @ResponseBody
    public Result<Boolean> checkParamCodeExists(@RequestParam String paramCode) {
        try {
            boolean exists = parameterService.existsByParamCode(paramCode);
            return Result.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查参数编码失败", e);
            return Result.<Boolean>fail("检查参数编码失败");
        }
    }

    /**
     * 检查参数名称是否存在
     */
    @GetMapping("/check/paramname")
    @ResponseBody
    public Result<Boolean> checkParamNameExists(@RequestParam String paramName) {
        try {
            boolean exists = parameterService.existsByParamName(paramName);
            return Result.success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查参数名称失败", e);
            return Result.<Boolean>fail("检查参数名称失败");
        }
    }

    /**
     * 导出参数配置
     */
    @GetMapping("/export")
    @ResponseBody
    public Result<String> exportParameters(@RequestParam(required = false) String paramGroup) {
        try {
            String configJson = parameterService.exportParameters(paramGroup);
            return Result.success("导出参数配置成功", configJson);
        } catch (Exception e) {
            log.error("导出参数配置失败", e);
            return Result.<String>fail("导出参数配置失败");
        }
    }

    /**
     * 导入参数配置
     */
    @PostMapping("/import")
    @ResponseBody
    public Result<String> importParameters(
            @RequestParam String configJson,
            @RequestParam(defaultValue = "false") Boolean isOverride) {
        try {
            String result = parameterService.importParameters(configJson, isOverride);
            return Result.success("导入参数配置成功", result);
        } catch (Exception e) {
            log.error("导入参数配置失败", e);
            return Result.<String>fail("导入参数配置失败: " + e.getMessage());
        }
    }

    /**
     * 批量更新参数值
     */
    @PutMapping("/batch-update")
    @ResponseBody
    public Result<Void> batchUpdateParamValues(@RequestBody Map<String, String> paramValueMap) {
        try {
            boolean success = parameterService.batchUpdateParamValues(paramValueMap);
            if (success) {
                return Result.success("批量更新参数值成功", (Void)null);
            } else {
                return Result.<Void>fail("部分参数值更新失败");
            }
        } catch (Exception e) {
            log.error("批量更新参数值失败", e);
            return Result.<Void>fail("批量更新参数值失败");
        }
    }

}