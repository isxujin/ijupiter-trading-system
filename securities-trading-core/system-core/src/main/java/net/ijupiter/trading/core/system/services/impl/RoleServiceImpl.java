package net.ijupiter.trading.core.system.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.common.exceptions.BusinessException;
import net.ijupiter.trading.api.system.dtos.RoleDTO;
import net.ijupiter.trading.core.system.entities.Permission;
import net.ijupiter.trading.core.system.entities.Role;
import net.ijupiter.trading.core.system.repositories.RoleRepository;
import net.ijupiter.trading.core.system.repositories.PermissionRepository;
import net.ijupiter.trading.core.system.repositories.UserRepository;
import net.ijupiter.trading.api.system.services.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Override
    public RoleDTO save(RoleDTO dto) {
        Role role = convertToEntity(dto);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public Optional<RoleDTO> findById(Long id) {
        return roleRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findAllById(List<Long> ids) {
        return roleRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return roleRepository.existsById(id);
    }

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void delete(RoleDTO dto) {
        Role role = roleRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("角色不存在"));
        roleRepository.delete(role);
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }

    @Override
    public RoleDTO saveAndFlush(RoleDTO dto) {
        Role role = convertToEntity(dto);
        Role savedRole = roleRepository.saveAndFlush(role);
        return convertToDTO(savedRole);
    }

    @Override
    public List<RoleDTO> saveAll(List<RoleDTO> dtos) {
        List<Role> roles = dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        List<Role> savedRoles = roleRepository.saveAll(roles);
        return savedRoles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDTO> findByRoleCode(String roleCode) {
        return roleRepository.findByRoleCode(roleCode)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<RoleDTO> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .map(this::convertToDTO);
    }

    @Override
    public List<RoleDTO> findByStatus(Integer status) {
        return roleRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findByUserId(Long userId) {
        return roleRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findByUserCode(String userCode) {
        return roleRepository.findByUserCode(userCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findByPermissionId(Long permissionId) {
        return roleRepository.findByPermissionId(permissionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findByPermissionCode(String permissionCode) {
        return roleRepository.findByPermissionCode(permissionCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByRoleCode(String roleCode) {
        return roleRepository.existsByRoleCode(roleCode);
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    @Override
    public List<RoleDTO> searchByKeyword(String keyword) {
        return roleRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findBySortOrderBetween(Integer minSortOrder, Integer maxSortOrder) {
        return roleRepository.findBySortOrderBetween(minSortOrder, maxSortOrder).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleDTO createRoleWithPermissions(RoleDTO roleDTO, List<Long> permissionIds) {
        // 验证角色编码和角色名唯一性
        if (existsByRoleCode(roleDTO.getRoleCode())) {
            throw new BusinessException("角色编码已存在：" + roleDTO.getRoleCode());
        }
        if (existsByRoleName(roleDTO.getRoleName())) {
            throw new BusinessException("角色名称已存在：" + roleDTO.getRoleName());
        }

        // 转换为实体
        Role role = convertToEntity(roleDTO);

        // 查找并分配权限
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<Permission> permissions = permissionRepository.findAllById(permissionIds);
            if (permissions.size() != permissionIds.size()) {
                throw new BusinessException("部分权限不存在");
            }
            role.setPermissions(new java.util.HashSet<>(permissions));
        }

        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    @Transactional
    public RoleDTO updateRoleInfo(RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(roleDTO.getId())
                .orElseThrow(() -> new BusinessException("角色不存在"));

        // 验证角色编码和角色名唯一性（排除当前角色）
        if (!existingRole.getRoleCode().equals(roleDTO.getRoleCode()) && 
            existsByRoleCode(roleDTO.getRoleCode())) {
            throw new BusinessException("角色编码已存在：" + roleDTO.getRoleCode());
        }
        if (!existingRole.getRoleName().equals(roleDTO.getRoleName()) && 
            existsByRoleName(roleDTO.getRoleName())) {
            throw new BusinessException("角色名称已存在：" + roleDTO.getRoleName());
        }

        // 更新角色基本信息
        existingRole.setRoleCode(roleDTO.getRoleCode());
        existingRole.setRoleName(roleDTO.getRoleName());
        existingRole.setDescription(roleDTO.getDescription());
        existingRole.setStatus(roleDTO.getStatus());
        existingRole.setSortOrder(roleDTO.getSortOrder());
        existingRole.setRemark(roleDTO.getRemark());

        Role savedRole = roleRepository.save(existingRole);
        return convertToDTO(savedRole);
    }

    @Override
    @Transactional
    public boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException("角色不存在"));

        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        if (permissions.size() != permissionIds.size()) {
            throw new BusinessException("部分权限不存在");
        }

        role.setPermissions(new java.util.HashSet<>(permissions));
        roleRepository.save(role);
        return true;
    }

    @Override
    @Transactional
    public boolean removePermissions(Long roleId, List<Long> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException("角色不存在"));

        List<Permission> permissionsToRemove = permissionRepository.findAllById(permissionIds);
        role.getPermissions().removeAll(permissionsToRemove);
        roleRepository.save(role);
        return true;
    }

    @Override
    @Transactional
    public boolean clearPermissions(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException("角色不存在"));

        role.getPermissions().clear();
        roleRepository.save(role);
        return true;
    }

    @Override
    @Transactional
    public boolean enableRole(Long roleId) {
        return updateRoleStatus(roleId, 1);
    }

    @Override
    @Transactional
    public boolean disableRole(Long roleId) {
        return updateRoleStatus(roleId, 0);
    }

    private boolean updateRoleStatus(Long roleId, Integer status) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException("角色不存在"));

        role.setStatus(status);
        roleRepository.save(role);
        return true;
    }

    @Override
    public Optional<RoleDTO> findRoleWithPermissions(String roleCode) {
        Optional<Role> roleOpt = roleRepository.findByRoleCode(roleCode);
        if (roleOpt.isEmpty()) {
            return Optional.empty();
        }

        Role role = roleOpt.get();
        RoleDTO roleDTO = convertToDTO(role);

        // 填充权限信息
        List<Long> permissionIds = new ArrayList<>();
        List<String> permissionNames = new ArrayList<>();
        for (Permission permission : role.getPermissions()) {
            permissionIds.add(permission.getId());
            permissionNames.add(permission.getPermissionName());
        }
        roleDTO.setPermissionIds(permissionIds);
        roleDTO.setPermissionNames(permissionNames);

        return Optional.of(roleDTO);
    }

    @Override
    public Optional<RoleDTO> findRoleWithUsers(String roleCode) {
        Optional<Role> roleOpt = roleRepository.findByRoleCode(roleCode);
        if (roleOpt.isEmpty()) {
            return Optional.empty();
        }

        Role role = roleOpt.get();
        RoleDTO roleDTO = convertToDTO(role);

        // 填充用户数量
        roleDTO.setUserCount(role.getUsers().size());

        return Optional.of(roleDTO);
    }

    @Override
    public boolean isSystemRole(Long roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isEmpty()) {
            return false;
        }

        Role role = roleOpt.get();
        // 这里可以根据实际业务规则判断，比如特定编码前缀的角色为系统角色
        return role.getRoleCode().startsWith("SYS_");
    }

    @Override
    public Integer getUserCount(Long roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isEmpty()) {
            return 0;
        }

        Role role = roleOpt.get();
        return role.getUsers().size();
    }

    /**
     * 将实体转换为DTO
     *
     * @param role 角色实体
     * @return 角色DTO
     */
    private RoleDTO convertToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO().convertFrom(role);

        // 填充权限信息
        if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
            List<Long> permissionIds = new ArrayList<>();
            List<String> permissionNames = new ArrayList<>();
            for (Permission permission : role.getPermissions()) {
                permissionIds.add(permission.getId());
                permissionNames.add(permission.getPermissionName());
            }
            roleDTO.setPermissionIds(permissionIds);
            roleDTO.setPermissionNames(permissionNames);
        }

        // 填充用户数量
        roleDTO.setUserCount(role.getUsers().size());

        // 设置是否为系统角色
        roleDTO.setIsSystemRole(role.getRoleCode().startsWith("SYS_"));

        return roleDTO;
    }

    /**
     * 将DTO转换为实体
     *
     * @param roleDTO 角色DTO
     * @return 角色实体
     */
    private Role convertToEntity(RoleDTO roleDTO) {
        return new Role().convertFrom(roleDTO);
    }
}