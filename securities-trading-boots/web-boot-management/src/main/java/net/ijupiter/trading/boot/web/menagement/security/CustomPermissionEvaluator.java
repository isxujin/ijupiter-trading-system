package net.ijupiter.trading.boot.web.menagement.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 自定义权限评估器，用于方法级别的权限控制
 */
@Slf4j
@Component("permissionEvaluator")
public class CustomPermissionEvaluator {

    /**
     * 检查用户是否有指定权限
     */
    public boolean hasPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        // 获取用户的所有权限
        Collection<String> permissions = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        
        // 检查是否有指定权限（去除ROLE_前缀）
        boolean hasPermission = permissions.contains("ROLE_" + permission) || 
                            permissions.contains(permission);
        
        log.debug("权限检查: {} -> {}", permission, hasPermission);
        
        return hasPermission;
    }
    
    /**
     * 检查用户是否有任意一个指定权限
     */
    public boolean hasAnyPermission(String... permissions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        // 获取用户的所有权限
        Collection<String> userPermissions = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        
        // 检查是否有任意一个指定权限
        for (String permission : permissions) {
            if (userPermissions.contains("ROLE_" + permission) || 
                userPermissions.contains(permission)) {
                log.debug("任意权限检查通过: {}", permission);
                return true;
            }
        }
        
        log.debug("任意权限检查失败，所需权限: {}", (Object) permissions);
        return false;
    }
    
    /**
     * 检查用户是否有所有指定权限
     */
    public boolean hasAllPermissions(String... permissions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        // 获取用户的所有权限
        Collection<String> userPermissions = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        
        // 检查是否有所有指定权限
        for (String permission : permissions) {
            if (!userPermissions.contains("ROLE_" + permission) && 
                !userPermissions.contains(permission)) {
                log.debug("全部权限检查失败，缺少权限: {}", permission);
                return false;
            }
        }
        
        log.debug("全部权限检查通过，所需权限: {}", (Object) permissions);
        return true;
    }
    
    /**
     * 检查用户是否有指定角色
     */
    public boolean hasRole(String role) {
        return hasPermission(role);
    }
    
    /**
     * 检查用户是否有任意一个指定角色
     */
    public boolean hasAnyRole(String... roles) {
        return hasAnyPermission(roles);
    }
    
    /**
     * 检查用户是否有所有指定角色
     */
    public boolean hasAllRoles(String... roles) {
        return hasAllPermissions(roles);
    }
}