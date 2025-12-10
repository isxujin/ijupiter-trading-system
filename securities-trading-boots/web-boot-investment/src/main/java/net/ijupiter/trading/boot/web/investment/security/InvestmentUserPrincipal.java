package net.ijupiter.trading.boot.web.investment.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ijupiter.trading.api.system.dtos.UserDTO;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;
import net.ijupiter.trading.api.system.dtos.RoleDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义用户主体，实现Spring Security的UserDetails接口
 * 封装用户、角色和权限信息
 */
@Data
@AllArgsConstructor
public class InvestmentUserPrincipal implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String userCode;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private Integer status;
    private Collection<? extends GrantedAuthority> authorities;
    
    /**
     * 通过UserDTO创建UserPrincipal
     */
    public static InvestmentUserPrincipal create(UserDTO userDTO, List<PermissionDTO> permissions, List<RoleDTO> roles) {
        // 创建权限列表
        Collection<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionCode()))
                .collect(Collectors.toList());
        
        // 添加角色权限
        roles.forEach(role -> 
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()))
        );
        
        return new InvestmentUserPrincipal(
                userDTO.getId(),
                userDTO.getUserCode(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRealName(),
                userDTO.getEmail(),
                userDTO.getPhone(),
                userDTO.getStatus(),
                authorities
        );
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        // 只有状态为1的用户才是启用的
        return status != null && status == 1;
    }
}