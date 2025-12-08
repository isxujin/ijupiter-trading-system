package net.ijupiter.trading.core.system.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.common.exceptions.BusinessException;
import net.ijupiter.trading.common.utils.StringUtils;
import net.ijupiter.trading.api.system.dtos.UserDTO;
import net.ijupiter.trading.core.system.entities.Role;
import net.ijupiter.trading.core.system.entities.User;
import net.ijupiter.trading.core.system.repositories.UserRepository;
import net.ijupiter.trading.core.system.repositories.RoleRepository;
import net.ijupiter.trading.api.system.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO save(UserDTO dto) {
        User user = convertToEntity(dto);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllById(List<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(UserDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("用户不存在"));
        userRepository.delete(user);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public UserDTO saveAndFlush(UserDTO dto) {
        User user = convertToEntity(dto);
        User savedUser = userRepository.saveAndFlush(user);
        return convertToDTO(savedUser);
    }

    @Override
    public List<UserDTO> saveAll(List<UserDTO> dtos) {
        List<User> users = dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        List<User> savedUsers = userRepository.saveAll(users);
        return savedUsers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findByUserCode(String userCode) {
        return userRepository.findByUserCode(userCode)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> findByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email)
                .map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> findByStatus(Integer status) {
        return userRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByRoleId(Long roleId) {
        return userRepository.findByRoleId(roleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByRoleCode(String roleCode) {
        return userRepository.findByRoleCode(roleCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUserCode(String userCode) {
        return userRepository.existsByUserCode(userCode);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public List<UserDTO> searchByKeyword(String keyword) {
        return userRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO createUserWithRoles(UserDTO userDTO, List<Long> roleIds) {
        // 验证用户编码和用户名唯一性
        if (existsByUserCode(userDTO.getUserCode())) {
            throw new BusinessException("用户编码已存在：" + userDTO.getUserCode());
        }
        if (existsByUsername(userDTO.getUsername())) {
            throw new BusinessException("用户名已存在：" + userDTO.getUsername());
        }
        if (StringUtils.isNotEmpty(userDTO.getEmail()) && existsByEmail(userDTO.getEmail())) {
            throw new BusinessException("邮箱已存在：" + userDTO.getEmail());
        }
        if (StringUtils.isNotEmpty(userDTO.getPhone()) && existsByPhone(userDTO.getPhone())) {
            throw new BusinessException("手机号已存在：" + userDTO.getPhone());
        }

        // 转换为实体并加密密码
        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 查找并分配角色
        if (roleIds != null && !roleIds.isEmpty()) {
            List<Role> roles = roleRepository.findAllById(roleIds);
            if (roles.size() != roleIds.size()) {
                throw new BusinessException("部分角色不存在");
            }
            user.setRoles(new java.util.HashSet<>(roles));
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUserInfo(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new BusinessException("用户不存在"));

        // 验证用户编码和用户名唯一性（排除当前用户）
        if (!existingUser.getUserCode().equals(userDTO.getUserCode()) && existsByUserCode(userDTO.getUserCode())) {
            throw new BusinessException("用户编码已存在：" + userDTO.getUserCode());
        }
        if (!existingUser.getUsername().equals(userDTO.getUsername()) && existsByUsername(userDTO.getUsername())) {
            throw new BusinessException("用户名已存在：" + userDTO.getUsername());
        }
        if (StringUtils.isNotEmpty(userDTO.getEmail()) && 
            !existingUser.getEmail().equals(userDTO.getEmail()) && 
            existsByEmail(userDTO.getEmail())) {
            throw new BusinessException("邮箱已存在：" + userDTO.getEmail());
        }
        if (StringUtils.isNotEmpty(userDTO.getPhone()) && 
            !existingUser.getPhone().equals(userDTO.getPhone()) && 
            existsByPhone(userDTO.getPhone())) {
            throw new BusinessException("手机号已存在：" + userDTO.getPhone());
        }

        // 更新用户基本信息
        existingUser.setUserCode(userDTO.getUserCode());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setRealName(userDTO.getRealName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setStatus(userDTO.getStatus());
        existingUser.setRemark(userDTO.getRemark());

        User savedUser = userRepository.save(existingUser);
        return convertToDTO(savedUser);
    }

    @Override
    @Transactional
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码不正确");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean assignRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            throw new BusinessException("部分角色不存在");
        }

        user.setRoles(new java.util.HashSet<>(roles));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean removeRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        List<Role> rolesToRemove = roleRepository.findAllById(roleIds);
        user.getRoles().removeAll(rolesToRemove);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean enableUser(Long userId) {
        return updateUserStatus(userId, 1);
    }

    @Override
    @Transactional
    public boolean disableUser(Long userId) {
        return updateUserStatus(userId, 0);
    }

    private boolean updateUserStatus(Long userId, Integer status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));

        user.setStatus(status);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean validatePassword(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public Optional<UserDTO> findUserWithRoles(String userCode) {
        Optional<User> userOpt = userRepository.findByUserCode(userCode);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        UserDTO userDTO = convertToDTO(user);

        // 填充角色信息
        List<Long> roleIds = new ArrayList<>();
        List<String> roleNames = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roleIds.add(role.getId());
            roleNames.add(role.getRoleName());
        }
        userDTO.setRoleIds(roleIds);
        userDTO.setRoleNames(roleNames);

        return Optional.of(userDTO);
    }

    @Override
    public Optional<UserDTO> findUserWithPermissions(String userCode) {
        // 这里可以通过查询用户的角色及权限来构建完整信息
        // 暂时返回基础用户信息
        return findUserWithRoles(userCode);
    }

    /**
     * 将实体转换为DTO
     *
     * @param user 用户实体
     * @return 用户DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO().convertFrom(user);

        // 填充角色信息
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            List<Long> roleIds = new ArrayList<>();
            List<String> roleNames = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roleIds.add(role.getId());
                roleNames.add(role.getRoleName());
            }
            userDTO.setRoleIds(roleIds);
            userDTO.setRoleNames(roleNames);
        }

        // 设置密码为null，避免在传输中泄露
        userDTO.setPassword(null);

        return userDTO;
    }

    /**
     * 将DTO转换为实体
     *
     * @param userDTO 用户DTO
     * @return 用户实体
     */
    private User convertToEntity(UserDTO userDTO) {
        return new User().convertFrom(userDTO);
    }
}