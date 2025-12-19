package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.UserDTO;
import net.ijupiter.trading.api.system.services.UserService;
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
 * 用户管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/system/user")
public class UserManageController extends BaseController {
    
    @Autowired
    private UserService userService;

    /**
     * 用户列表页面
     */
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("system/user/list");

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 获取用户列表数据
     */
    @GetMapping("/data")
    @ResponseBody
    public PageResult<UserDTO> getUserList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (username != null && !username.trim().isEmpty()) {
                searchParams.put("username", username);
            }
            if (email != null && !email.trim().isEmpty()) {
                searchParams.put("email", email);
            }
            if (status != null && !status.trim().isEmpty()) {
                searchParams.put("status", status);
            }
            
            // 使用基础服务方法获取所有数据
            List<UserDTO> allUsers = userService.findAll();
            // 模拟分页数据
            int total = allUsers.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<UserDTO> pageData = allUsers.subList(fromIndex, toIndex);
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            return PageResult.failPage("获取用户列表失败");
        }
    }

    /**
     * 添加用户页面
     */
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("system/user/add");

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 编辑用户页面
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("system/user/edit");
        
        try {
 UserDTO user = userService.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
            modelAndView.addObject("user", user);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            modelAndView.addObject("error", "获取用户信息失败");
        }

        modelAndView.addObject("activeModule", "system");
        
        return modelAndView;
    }

    /**
     * 保存用户
     */
    @PostMapping("/save")
    @ResponseBody
    public ApiResponse<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO savedUser = userService.save(userDTO);
            return ApiResponse.success("用户保存成功", savedUser);
        } catch (Exception e) {
            log.error("保存用户失败", e);
            return ApiResponse.<UserDTO>error("保存用户失败");
        }
    }

    /**
     * 更新用户
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public ApiResponse<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            userDTO.setId(id);
            UserDTO updatedUser = userService.updateUserInfo(userDTO);
            return ApiResponse.success("用户更新成功", updatedUser);
        } catch (Exception e) {
            log.error("更新用户失败", e);
            return ApiResponse.<UserDTO>error("更新用户失败");
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ApiResponse.success("用户删除成功", (Void)null);
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return ApiResponse.<Void>error("删除用户失败");
        }
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public ApiResponse<Void> batchDeleteUsers(@RequestBody List<Long> ids) {
        try {
            // BaseService中没有deleteByIds方法，需要循环删除
            for (Long id : ids) {
                userService.deleteById(id);
            }
            return ApiResponse.success("批量删除用户成功", (Void)null);
        } catch (Exception e) {
            log.error("批量删除用户失败", e);
            return ApiResponse.error("批量删除用户失败");
        }
    }

    /**
     * 启用/禁用用户
     */
    @PutMapping("/status/{id}")
    @ResponseBody
    public ApiResponse<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            UserDTO user = userService.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
            user.setStatus(status);
            userService.updateUserInfo(user);
            return ApiResponse.success("用户状态更新成功", (Void)null);
        } catch (Exception e) {
            log.error("更新用户状态失败", e);
            return ApiResponse.error("更新用户状态失败");
        }
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/check/username")
    @ResponseBody
    public ApiResponse<Boolean> checkUsernameExists(@RequestParam String username) {
        try {
            boolean exists = userService.existsByUsername(username);
            return ApiResponse.<Boolean>success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查用户名失败", e);
            return ApiResponse.error("检查用户名失败");
        }
    }

    /**
     * 检查邮箱是否存在
     */
    @GetMapping("/check/email")
    @ResponseBody
    public ApiResponse<Boolean> checkEmailExists(@RequestParam String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return ApiResponse.<Boolean>success("检查完成", exists);
        } catch (Exception e) {
            log.error("检查邮箱失败", e);
            return ApiResponse.error("检查邮箱失败");
        }
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/reset-password/{id}")
    @ResponseBody
    public ApiResponse<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        try {
 UserDTO user = userService.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
            user.setPassword(newPassword);
            userService.updateUserInfo(user);
            return ApiResponse.success("密码重置成功", (Void)null);
        } catch (Exception e) {
            log.error("重置密码失败", e);
            return ApiResponse.error("重置密码失败");
        }
    }
}