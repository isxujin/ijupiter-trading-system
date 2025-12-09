package net.ijupiter.trading.boot.web.menagement.utils;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.boot.web.menagement.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

/**
 * 安全工具类，用于获取当前用户信息
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录用户
     */
    public static Optional<UserPrincipal> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return Optional.of((UserPrincipal) authentication.getPrincipal());
        }
        
        return Optional.empty();
    }
    
    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().map(UserPrincipal::getId).orElse(null);
    }
    
    /**
     * 获取当前用户编码
     */
    public static String getCurrentUserCode() {
        return getCurrentUser().map(UserPrincipal::getUserCode).orElse(null);
    }
    
    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        return getCurrentUser().map(UserPrincipal::getUsername).orElse(null);
    }
    
    /**
     * 获取当前用户真实姓名
     */
    public static String getCurrentRealName() {
        return getCurrentUser().map(UserPrincipal::getRealName).orElse(null);
    }
    
    /**
     * 获取当前用户邮箱
     */
    public static String getCurrentEmail() {
        return getCurrentUser().map(UserPrincipal::getEmail).orElse(null);
    }
    
    /**
     * 获取当前用户手机号
     */
    public static String getCurrentPhone() {
        return getCurrentUser().map(UserPrincipal::getPhone).orElse(null);
    }
    
    /**
     * 获取当前用户的所有权限
     */
    public static Collection<String> getCurrentUserAuthorities() {
        return getCurrentUser()
                .map(user -> user.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .collect(java.util.stream.Collectors.toList()))
                .orElse(java.util.Collections.emptyList());
    }
    
    /**
     * 检查当前用户是否已认证
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && 
               authentication.isAuthenticated() && 
               !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken);
    }
    
    /**
     * 检查当前用户是否有指定权限
     */
    public static boolean hasPermission(String permission) {
        if (!isAuthenticated()) {
            return false;
        }
        
        Collection<String> authorities = getCurrentUserAuthorities();
        return authorities.contains("ROLE_" + permission) || authorities.contains(permission);
    }
    
    /**
     * 检查当前用户是否有任意一个指定权限
     */
    public static boolean hasAnyPermission(String... permissions) {
        if (!isAuthenticated()) {
            return false;
        }
        
        Collection<String> authorities = getCurrentUserAuthorities();
        for (String permission : permissions) {
            if (authorities.contains("ROLE_" + permission) || authorities.contains(permission)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 检查当前用户是否有所有指定权限
     */
    public static boolean hasAllPermissions(String... permissions) {
        if (!isAuthenticated()) {
            return false;
        }
        
        Collection<String> authorities = getCurrentUserAuthorities();
        for (String permission : permissions) {
            if (!authorities.contains("ROLE_" + permission) && !authorities.contains(permission)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 检查当前用户是否有指定角色
     */
    public static boolean hasRole(String role) {
        return hasPermission(role);
    }
    
    /**
     * 检查当前用户是否有任意一个指定角色
     */
    public static boolean hasAnyRole(String... roles) {
        return hasAnyPermission(roles);
    }
    
    /**
     * 检查当前用户是否有所有指定角色
     */
    public static boolean hasAllRoles(String... roles) {
        return hasAllPermissions(roles);
    }
}