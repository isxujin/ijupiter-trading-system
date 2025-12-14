package net.ijupiter.trading.core.system.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.common.exceptions.BusinessException;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;
import net.ijupiter.trading.core.system.entities.Permission;
import net.ijupiter.trading.core.system.repositories.PermissionRepository;
import net.ijupiter.trading.api.system.services.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限服务实现类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionDTO save(PermissionDTO dto) {
        Permission permission = convertToEntity(dto);
        Permission savedPermission = permissionRepository.save(permission);
        return convertToDTO(savedPermission);
    }

    @Override
    public Optional<PermissionDTO> findById(Long id) {
        return permissionRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<PermissionDTO> findAll() {
        return permissionRepository.findAllWithRoles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findAllById(List<Long> ids) {
        return permissionRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return permissionRepository.existsById(id);
    }

    @Override
    public long count() {
        return permissionRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public void delete(PermissionDTO dto) {
        Permission permission = permissionRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("权限不存在"));
        permissionRepository.delete(permission);
    }

    @Override
    public void deleteAll() {
        permissionRepository.deleteAll();
    }

    @Override
    public PermissionDTO saveAndFlush(PermissionDTO dto) {
        Permission permission = convertToEntity(dto);
        Permission savedPermission = permissionRepository.saveAndFlush(permission);
        return convertToDTO(savedPermission);
    }

    @Override
    public List<PermissionDTO> saveAll(List<PermissionDTO> dtos) {
        List<Permission> permissions = dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        List<Permission> savedPermissions = permissionRepository.saveAll(permissions);
        return savedPermissions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionDTO> findByPermissionCode(String permissionCode) {
        return permissionRepository.findByPermissionCode(permissionCode)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<PermissionDTO> findByPermissionName(String permissionName) {
        return permissionRepository.findByPermissionName(permissionName)
                .map(this::convertToDTO);
    }

    @Override
    public List<PermissionDTO> findByPermissionType(String permissionType) {
        return permissionRepository.findByPermissionType(permissionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findByStatus(Integer status) {
        return permissionRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findByParentId(Long parentId) {
        return permissionRepository.findByParentId(parentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findRootPermissions() {
        return permissionRepository.findRootPermissions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findByRoleId(Long roleId) {
        return permissionRepository.findByRoleId(roleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findByRoleCode(String roleCode) {
        return permissionRepository.findByRoleCode(roleCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findByUserId(Long userId) {
        return permissionRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findByUserCode(String userCode) {
        return permissionRepository.findByUserCode(userCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByPermissionCode(String permissionCode) {
        return permissionRepository.existsByPermissionCode(permissionCode);
    }

    @Override
    public boolean existsByPermissionName(String permissionName) {
        return permissionRepository.existsByPermissionName(permissionName);
    }

    @Override
    public List<PermissionDTO> searchByKeyword(String keyword) {
        return permissionRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findBySortOrderBetween(Integer minSortOrder, Integer maxSortOrder) {
        return permissionRepository.findBySortOrderBetween(minSortOrder, maxSortOrder).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findMenuPermissions() {
        return permissionRepository.findMenuPermissions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findButtonPermissions() {
        return permissionRepository.findButtonPermissions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findApiPermissions() {
        return permissionRepository.findApiPermissions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        // 验证权限编码和权限名唯一性
        if (existsByPermissionCode(permissionDTO.getPermissionCode())) {
            throw new BusinessException("权限编码已存在：" + permissionDTO.getPermissionCode());
        }
        if (existsByPermissionName(permissionDTO.getPermissionName())) {
            throw new BusinessException("权限名称已存在：" + permissionDTO.getPermissionName());
        }

        // 验证父权限是否存在
        if (permissionDTO.getParentId() != null && 
            permissionDTO.getParentId() > 0 && 
            !existsById(permissionDTO.getParentId())) {
            throw new BusinessException("父权限不存在：" + permissionDTO.getParentId());
        }

        // 转换为实体
        Permission permission = convertToEntity(permissionDTO);
        Permission savedPermission = permissionRepository.save(permission);
        return convertToDTO(savedPermission);
    }

    @Override
    @Transactional
    public PermissionDTO updatePermission(PermissionDTO permissionDTO) {
        Permission existingPermission = permissionRepository.findById(permissionDTO.getId())
                .orElseThrow(() -> new BusinessException("权限不存在"));

        // 验证权限编码和权限名唯一性（排除当前权限）
        if (!existingPermission.getPermissionCode().equals(permissionDTO.getPermissionCode()) && 
            existsByPermissionCode(permissionDTO.getPermissionCode())) {
            throw new BusinessException("权限编码已存在：" + permissionDTO.getPermissionCode());
        }
        if (!existingPermission.getPermissionName().equals(permissionDTO.getPermissionName()) && 
            existsByPermissionName(permissionDTO.getPermissionName())) {
            throw new BusinessException("权限名称已存在：" + permissionDTO.getPermissionName());
        }

        // 验证父权限是否存在（排除自身）
        if (permissionDTO.getParentId() != null && 
            permissionDTO.getParentId() > 0 && 
            !permissionDTO.getParentId().equals(permissionDTO.getId()) &&
            !existsById(permissionDTO.getParentId())) {
            throw new BusinessException("父权限不存在：" + permissionDTO.getParentId());
        }

        // 验证是否会造成循环引用
        if (permissionDTO.getParentId() != null && 
            permissionDTO.getParentId() > 0 && 
            willCreateCircularReference(permissionDTO.getId(), permissionDTO.getParentId())) {
            throw new BusinessException("不能将权限设置为自己的子权限");
        }

        // 更新权限基本信息
        existingPermission.setPermissionCode(permissionDTO.getPermissionCode());
        existingPermission.setPermissionName(permissionDTO.getPermissionName());
        existingPermission.setDescription(permissionDTO.getDescription());
        existingPermission.setPermissionType(permissionDTO.getPermissionType());
        existingPermission.setParentId(permissionDTO.getParentId());
        existingPermission.setPath(permissionDTO.getPath());
        existingPermission.setIcon(permissionDTO.getIcon());
        existingPermission.setSortOrder(permissionDTO.getSortOrder());
        existingPermission.setStatus(permissionDTO.getStatus());
        existingPermission.setRemark(permissionDTO.getRemark());

        Permission savedPermission = permissionRepository.save(existingPermission);
        return convertToDTO(savedPermission);
    }

    @Override
    @Transactional
    public boolean enablePermission(Long permissionId) {
        return updatePermissionStatus(permissionId, 1);
    }

    @Override
    @Transactional
    public boolean disablePermission(Long permissionId) {
        return updatePermissionStatus(permissionId, 0);
    }

    private boolean updatePermissionStatus(Long permissionId, Integer status) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new BusinessException("权限不存在"));

        permission.setStatus(status);
        permissionRepository.save(permission);
        return true;
    }

    @Override
    public List<PermissionDTO> buildPermissionTree(List<PermissionDTO> permissions) {
        Map<Long, PermissionDTO> permissionMap = new HashMap<>();
        List<PermissionDTO> rootPermissions = new ArrayList<>();

        // 将所有权限放入Map
        for (PermissionDTO permission : permissions) {
            permissionMap.put(permission.getId(), permission);
        }

        // 构建树形结构
        for (PermissionDTO permission : permissions) {
            Long parentId = permission.getParentId();
            if (parentId == null || parentId == 0) {
                rootPermissions.add(permission);
            } else {
                PermissionDTO parent = permissionMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(permission);
                }
            }
        }

        return rootPermissions;
    }

    @Override
    public List<PermissionDTO> getPermissionTree() {
        List<PermissionDTO> allPermissions = findAll();
        return buildPermissionTree(allPermissions);
    }

    @Override
    public List<PermissionDTO> getMenuPermissionTree(String userCode) {
        List<PermissionDTO> userMenuPermissions = findByUserCode(userCode).stream()
                .filter(permission -> "menu".equals(permission.getPermissionType()) && permission.getStatus() == 1)
                .collect(Collectors.toList());

        return buildPermissionTree(userMenuPermissions);
    }

    @Override
    public boolean hasPermission(String userCode, String permissionCode) {
        List<PermissionDTO> userPermissions = findByUserCode(userCode);
        return userPermissions.stream()
                .anyMatch(permission -> permissionCode.equals(permission.getPermissionCode()));
    }

    @Override
    public boolean hasAnyPermission(String userCode, List<String> permissionCodes) {
        List<PermissionDTO> userPermissions = findByUserCode(userCode);
        Set<String> userPermissionCodes = userPermissions.stream()
                .map(PermissionDTO::getPermissionCode)
                .collect(Collectors.toSet());

        return permissionCodes.stream()
                .anyMatch(userPermissionCodes::contains);
    }

    @Override
    public boolean hasAllPermissions(String userCode, List<String> permissionCodes) {
        List<PermissionDTO> userPermissions = findByUserCode(userCode);
        Set<String> userPermissionCodes = userPermissions.stream()
                .map(PermissionDTO::getPermissionCode)
                .collect(Collectors.toSet());

        return permissionCodes.stream()
                .allMatch(userPermissionCodes::contains);
    }

    @Override
    @Transactional
    public boolean movePermission(Long permissionId, Long newParentId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new BusinessException("权限不存在"));

        // 验证新父权限是否存在
        if (newParentId != null && newParentId > 0 && !existsById(newParentId)) {
            throw new BusinessException("新父权限不存在：" + newParentId);
        }

        // 验证是否会造成循环引用
        if (newParentId != null && newParentId > 0 && willCreateCircularReference(permissionId, newParentId)) {
            throw new BusinessException("移动会导致权限循环引用");
        }

        permission.setParentId(newParentId);
        permissionRepository.save(permission);
        return true;
    }

    @Override
    public Integer getChildrenCount(Long permissionId) {
        return permissionRepository.findByParentId(permissionId).size();
    }

    /**
     * 判断是否会造成循环引用
     *
     * @param permissionId 权限ID
     * @param newParentId 新父权限ID
     * @return 是否会造成循环引用
     */
    private boolean willCreateCircularReference(Long permissionId, Long newParentId) {
        Long currentParentId = newParentId;
        while (currentParentId != null && currentParentId > 0) {
            if (currentParentId.equals(permissionId)) {
                return true;
            }

            Optional<Permission> parentOpt = permissionRepository.findById(currentParentId);
            if (parentOpt.isEmpty()) {
                break;
            }

            currentParentId = parentOpt.get().getParentId();
        }
        return false;
    }

    /**
     * 将实体转换为DTO
     *
     * @param permission 权限实体
     * @return 权限DTO
     */
    private PermissionDTO convertToDTO(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO().convertFrom(permission);

        // 设置是否为叶子节点
        List<Permission> children = permissionRepository.findByParentId(permission.getId());
        permissionDTO.setIsLeaf(children.isEmpty());

        // 设置权限级别
        int level = 0;
        Long currentParentId = permission.getParentId();
        while (currentParentId != null && currentParentId > 0) {
            level++;
            Optional<Permission> parentOpt = permissionRepository.findById(currentParentId);
            if (parentOpt.isEmpty()) {
                break;
            }
            currentParentId = parentOpt.get().getParentId();
        }
        permissionDTO.setLevel(level);

        // 设置关联的角色数量
        permissionDTO.setRoleCount(permission.getRoles().size());

        return permissionDTO;
    }

    /**
     * 将DTO转换为实体
     *
     * @param permissionDTO 权限DTO
     * @return 权限实体
     */
    private Permission convertToEntity(PermissionDTO permissionDTO) {
        return new Permission().convertFrom(permissionDTO);
    }
}