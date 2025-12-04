package net.ijupiter.trading.core.system.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.system.api.dto.RoleDTO;
import net.ijupiter.trading.system.api.dto.RoleQueryDTO;
import net.ijupiter.trading.system.api.services.RoleService;
import net.ijupiter.trading.core.system.entities.Role;
import net.ijupiter.trading.core.system.repositories.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 角色服务实现
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 实体转DTO
     */
    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        
        // 手动复制属性，避免时间字段问题
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        dto.setRoleCode(role.getRoleCode());
        dto.setDescription(role.getDescription());
        dto.setStatus(role.getStatus());
        dto.setIsSystem(role.getIsSystem());
        
        // 时间字段从实体复制到DTO
        dto.setCreateTime(role.getCreateTime());
        dto.setUpdateTime(role.getUpdateTime());
        
        return dto;
    }

    /**
     * DTO转实体
     */
    private Role convertToEntity(RoleDTO dto) {
        Role entity = new Role();
        
        // 手动复制属性，避免时间字段问题
        entity.setRoleId(dto.getRoleId());
        entity.setRoleName(dto.getRoleName());
        entity.setRoleCode(dto.getRoleCode());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setIsSystem(dto.getIsSystem());
        
        // 时间字段从DTO复制，确保正确处理
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> queryRoles(RoleQueryDTO queryDTO) {
        // 实现查询逻辑
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(String roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        return role.map(this::convertToDTO).orElse(null);
    }

    @Override
    public RoleDTO getRoleByCode(String roleCode) {
        // 使用Optional包装可能不是Optional的返回值
        Role role = roleRepository.findByRoleCode(roleCode).get();
        if (role != null) {
            return convertToDTO(role);
        }
        return null;
    }

    @Override
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        log.debug("创建角色: {}", roleDTO);
        
        Role role = convertToEntity(roleDTO);
        role.setCreateTime(LocalDateTime.now());
        Role savedRole = roleRepository.save(role);
        
        return convertToDTO(savedRole);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        log.debug("更新角色: {}", roleDTO);
        
        Role role = roleRepository.findById(roleDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("角色不存在: " + roleDTO.getRoleId()));
        
        // 更新字段
        role.setRoleName(roleDTO.getRoleName());
        role.setRoleCode(roleDTO.getRoleCode());
        role.setDescription(roleDTO.getDescription());
        role.setStatus(roleDTO.getStatus());
        role.setIsSystem(roleDTO.getIsSystem());
        role.setUpdateTime(LocalDateTime.now());
        
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public Boolean deleteRole(String roleId) {
        log.debug("删除角色: {}", roleId);
        
        if (roleRepository.existsById(roleId)) {
            roleRepository.deleteById(roleId);
            return true;
        }
        return false;
    }

    @Override
    public Integer batchDeleteRoles(List<String> roleIds) {
        log.debug("批量删除角色: {}", roleIds);
        
        int deletedCount = 0;
        for (String roleId : roleIds) {
            if (deleteRole(roleId)) {
                deletedCount++;
            }
        }
        return deletedCount;
    }

    @Override
    public Boolean updateRoleStatus(String roleId, Integer status) {
        log.debug("更新角色状态: roleId={}, status={}", roleId, status);
        
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("角色不存在: " + roleId));
        
        role.setStatus(status);
        role.setUpdateTime(LocalDateTime.now());
        roleRepository.save(role);
        
        return true;
    }

    @Override
    public Boolean assignPermissions(String roleId, List<String> permissionIds) {
        log.debug("为角色分配权限: roleId={}, permissions={}", roleId, permissionIds);
        
        // 实现权限分配逻辑
        // 这里需要实现角色-权限关联表的插入操作
        
        return true;
    }

    @Override
    public Boolean revokePermissions(String roleId, List<String> permissionIds) {
        log.debug("回收角色权限: roleId={}, permissions={}", roleId, permissionIds);
        
        // 实现权限回收逻辑
        // 这里需要实现角色-权限关联表的删除操作
        
        return true;
    }

    // ==================== BaseService接口实现 ====================

    @Override
    @Transactional
    public RoleDTO save(RoleDTO entity) {
        log.debug("保存角色实体: {}", entity);
        Role role = convertToEntity(entity);
        if (role.getCreateTime() == null) {
            role.setCreateTime(LocalDateTime.now());
        }
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public Optional<RoleDTO> findById(String id) {
        log.debug("根据ID查询角色: {}", id);
        return roleRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<RoleDTO> findAll() {
        log.debug("查询所有角色");
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        log.debug("检查角色是否存在: {}", id);
        return roleRepository.existsById(id);
    }

    @Override
    public long count() {
        log.debug("统计角色数量");
        return roleRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.info("删除角色: {}", id);
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(RoleDTO entity) {
        log.info("删除角色实体: {}", entity);
        if (entity.getRoleId() != null) {
            roleRepository.deleteById(entity.getRoleId());
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.info("删除所有角色");
        roleRepository.deleteAll();
    }

    @Override
    @Transactional
    public RoleDTO saveAndFlush(RoleDTO entity) {
        log.debug("保存并刷新角色实体: {}", entity);
        Role role = convertToEntity(entity);
        if (role.getCreateTime() == null) {
            role.setCreateTime(LocalDateTime.now());
        }
        Role savedRole = roleRepository.saveAndFlush(role);
        return convertToDTO(savedRole);
    }

    @Override
    @Transactional
    public List<RoleDTO> saveAll(List<RoleDTO> entities) {
        log.debug("批量保存角色实体: {}", entities.size());
        List<Role> roles = entities.stream()
                .map(this::convertToEntity)
                .collect(java.util.stream.Collectors.toList());
        
        // 设置创建时间
        LocalDateTime now = LocalDateTime.now();
        roles.forEach(role -> {
            if (role.getCreateTime() == null) {
                role.setCreateTime(now);
            }
        });
        
        List<Role> savedRoles = roleRepository.saveAll(roles);
        return savedRoles.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<RoleDTO> findAllById(List<String> ids) {
        log.debug("根据ID列表查询角色: {}", ids);
        return roleRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}