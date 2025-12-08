package net.ijupiter.trading.web.system.configs;

import net.ijupiter.trading.api.system.services.UserService;
import net.ijupiter.trading.api.system.services.RoleService;
import net.ijupiter.trading.api.system.services.PermissionService;
import net.ijupiter.trading.api.system.services.DictionaryService;
import net.ijupiter.trading.web.system.controllers.SystemController;
import net.ijupiter.trading.web.system.controllers.UserManageController;
import net.ijupiter.trading.web.system.controllers.RoleManageController;
import net.ijupiter.trading.web.system.controllers.PermissionManageController;
import net.ijupiter.trading.web.system.controllers.DictionaryManageController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统Web模块自动配置类
 */
@Configuration
public class SystemAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SystemController systemController() {
        return new SystemController();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserManageController userManageController(UserService userService) {
        UserManageController controller = new UserManageController();
        // 通过反射设置service字段
        try {
            java.lang.reflect.Field field = UserManageController.class.getDeclaredField("userService");
            field.setAccessible(true);
            field.set(controller, userService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return controller;
    }

    @Bean
    @ConditionalOnMissingBean
    public RoleManageController roleManageController(RoleService roleService, PermissionService permissionService) {
        RoleManageController controller = new RoleManageController();
        // 通过反射设置service字段
        try {
            java.lang.reflect.Field roleServiceField = RoleManageController.class.getDeclaredField("roleService");
            roleServiceField.setAccessible(true);
            roleServiceField.set(controller, roleService);
            
            java.lang.reflect.Field permissionServiceField = RoleManageController.class.getDeclaredField("permissionService");
            permissionServiceField.setAccessible(true);
            permissionServiceField.set(controller, permissionService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return controller;
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionManageController permissionManageController(PermissionService permissionService) {
        PermissionManageController controller = new PermissionManageController();
        // 通过反射设置service字段
        try {
            java.lang.reflect.Field field = PermissionManageController.class.getDeclaredField("permissionService");
            field.setAccessible(true);
            field.set(controller, permissionService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return controller;
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryManageController dictionaryManageController(DictionaryService dictionaryService) {
        DictionaryManageController controller = new DictionaryManageController();
        // 通过反射设置service字段
        try {
            java.lang.reflect.Field field = DictionaryManageController.class.getDeclaredField("dictionaryService");
            field.setAccessible(true);
            field.set(controller, dictionaryService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return controller;
    }
}