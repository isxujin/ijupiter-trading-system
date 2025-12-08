package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户编码查找用户
     *
     * @param userCode 用户编码
     * @return 用户对象
     */
    Optional<User> findByUserCode(String userCode);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据手机号查找用户
     *
     * @param phone 手机号
     * @return 用户对象
     */
    Optional<User> findByPhone(String phone);

    /**
     * 根据用户名或邮箱查找用户
     *
     * @param username 用户名
     * @param email 邮箱
     * @return 用户对象
     */
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 根据状态查找用户列表
     *
     * @param status 状态
     * @return 用户列表
     */
    List<User> findByStatus(Integer status);

    /**
     * 根据角色ID查找用户列表
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId")
    List<User> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色编码查找用户列表
     *
     * @param roleCode 角色编码
     * @return 用户列表
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleCode = :roleCode")
    List<User> findByRoleCode(@Param("roleCode") String roleCode);

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
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.realName LIKE %:keyword% " +
           "OR u.email LIKE %:keyword% OR u.phone LIKE %:keyword%")
    List<User> searchByKeyword(@Param("keyword") String keyword);
}