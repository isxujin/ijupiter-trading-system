package net.ijupiter.trading.boot.web.investment.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.system.dtos.UserDTO;
import net.ijupiter.trading.api.system.dtos.RoleDTO;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;
import net.ijupiter.trading.api.system.services.UserService;
import net.ijupiter.trading.api.system.services.PermissionService;
import net.ijupiter.trading.api.system.services.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自定义用户详情服务，从数据库加载用户信息
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvestmentUserDetailsService implements UserDetailsService {
    
    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    
    /**
     * 根据用户名加载用户详情
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("正在加载用户信息，用户名: {}", username);
        
        // 从数据库查找用户
        UserDTO userDTO = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
        
        // 使用用户编码获取用户角色信息
        String userCode = userDTO.getUserCode();
        List<RoleDTO> roles = roleService.findByUserCode(userCode);
        
        // 使用用户编码获取用户权限信息
        List<PermissionDTO> permissions = permissionService.findByUserCode(userCode);
        
        log.debug("成功加载用户信息，用户ID: {}, 角色数量: {}, 权限数量: {}", 
                userDTO.getId(), 
                roles.size(), 
                permissions.size());
        
        // 转换为UserPrincipal
        return InvestmentUserPrincipal.create(userDTO, permissions, roles);
    }
}