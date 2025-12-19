package net.ijupiter.trading.boot.web.menagement.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import net.ijupiter.trading.boot.web.menagement.security.CustomUserDetailsService;

/**
 * Spring Security配置类
 * 配置基于RBAC3模型的认证和授权
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * 密码编码器（必须显式配置，6.x 强制要求）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * 认证提供者，确保使用正确的密码编码器
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * 安全过滤器链配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 允许访问的路径
                        .requestMatchers("/management/login", "/webjars/**", "/static/**", "/error", "/common/**", "/layout/**").permitAll()
                        
                        // 管理页面需要认证
                        .requestMatchers("/management/**").authenticated()
                        
                        // 其他请求都需要认证
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .formLogin(form -> form
                        .loginPage("/management/login")
                        .defaultSuccessUrl("/management/dashboard", true)
                        .failureUrl("/management/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/management/logout")
                        .logoutSuccessUrl("/management/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .sessionManagement(session -> session
                        .maximumSessions(1) // 限制用户同时登录的会话数
                        .maxSessionsPreventsLogin(false) // 不阻止新登录，而是使旧会话失效
                        .sessionRegistry(sessionRegistry())
                )
                .rememberMe(remember -> remember
                        .key("ijupiter-trading-remember-me")
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // 7天
                        .rememberMeParameter("remember-me")
                        .userDetailsService(customUserDetailsService)
                )
                // 在开发环境完全禁用CSRF，生产环境建议启用
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
    
    /**
     * 会话注册表，用于管理用户会话
     */
    @Bean
    public org.springframework.security.core.session.SessionRegistry sessionRegistry() {
        return new org.springframework.security.core.session.SessionRegistryImpl();
    }
    
    /**
     * HTTP会话事件发布器，用于监听会话事件
     */
    @Bean
    public org.springframework.security.web.session.HttpSessionEventPublisher httpSessionEventPublisher() {
        return new org.springframework.security.web.session.HttpSessionEventPublisher();
    }
}