package net.ijupiter.trading.api.system.services;

import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.system.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface UserService extends BaseService<UserDTO, Long> {

    /**
     * 根据用户编码查找用户
     *
     * @param userCode 用户编码
     * @return 用户DTO
     */
    Optional<UserDTO> findByUserCode(String userCode);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户DTO
     */
    Optional<UserDTO> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户DTO
     */
    Optional<UserDTO> findByEmail(String email);

    /**
     * 根据手机号查找用户
     *
     * @param phone 手机号
     * @return 用户DTO
     */
    Optional<UserDTO> findByPhone(String phone);

    /**
     * 根据用户名或邮箱查找用户
     *
     * @param username 用户名
     * @param email 邮箱
     * @return 用户DTO
     */
    Optional<UserDTO> findByUsernameOrEmail(String username, String email);

    /**
     * 根据状态查找用户列表
     *
     * @param status 状态
     * @return 用户DTO列表
     */
    List<UserDTO> findByStatus(Integer status);

    /**
     * 根据角色ID查找用户列表
     *
     * @param roleId 角色ID
     * @return 用户DTO列表
     */
    List<UserDTO> findByRoleId(Long roleId);

    /**
     * 根据角色编码查找用户列表
     *
     * @param roleCode 角色编码
     * @return 用户DTO列表
     */
    List<UserDTO> findByRoleCode(String roleCode);

    /**
     * 检查用户编码是否存在
     *
     * @param userCode 用户编码
     * @return 是否存在
     */
    boolean existsByUserCode(String userCode);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 模糊搜索用户（支持用户名、真实姓名、邮箱、手机号）
     *
     * @param keyword 关键字
     * @return 用户DTO列表
     */
    List<UserDTO> searchByKeyword(String keyword);

    /**
     * 创建用户并分配角色
     *
     * @param userDTO 用户DTO
     * @param roleIds 角色ID列表
     * @return 创建后的用户DTO
     */
    UserDTO createUserWithRoles(UserDTO userDTO, List<Long> roleIds);

    /**
     * 更新用户基本信息
     *
     * @param userDTO 用户DTO
     * @return 更新后的用户DTO
     */
    UserDTO updateUserInfo(UserDTO userDTO);

    /**
     * 更新用户密码
     *
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否更新成功
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 重置用户密码
     *
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 分配角色给用户
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否分配成功
     */
    boolean assignRoles(Long userId, List<Long> roleIds);

    /**
     * 移除用户的角色
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否移除成功
     */
    boolean removeRoles(Long userId, List<Long> roleIds);

    /**
     * 启用用户
     *
     * @param userId 用户ID
     * @return 是否启用成功
     */
    boolean enableUser(Long userId);

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     * @return 是否禁用成功
     */
    boolean disableUser(Long userId);

    /**
     * 验证用户密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否验证成功
     */
    boolean validatePassword(String username, String password);

    /**
     * 根据用户编码查找用户及其角色信息
     *
     * @param userCode 用户编码
     * @return 用户DTO（包含角色信息）
     */
    Optional<UserDTO> findUserWithRoles(String userCode);

    /**
     * 根据用户编码查找用户及其权限信息
     *
     * @param userCode 用户编码
     * @return 用户DTO（包含权限信息）
     */
    Optional<UserDTO> findUserWithPermissions(String userCode);
}