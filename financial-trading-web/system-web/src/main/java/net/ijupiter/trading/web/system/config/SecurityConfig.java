package net.ijupiter.trading.web.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 密码编码器（必须显式配置，6.x 强制要求）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 内存用户配置（替代yml中的用户配置，避免配置不生效）
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // 明文密码：management@123，加密后存储（也可直接用加密串）
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin@123")) // 动态加密，避免串不匹配
                .roles("ADMIN") // 角色名，会自动拼接 ROLE_ 前缀
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/management/login", "/webjars/**", "/static/**", "/error", "/common/**", "/test-error").permitAll()
                        .requestMatchers("/management/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/management/login")
                        .defaultSuccessUrl("/management/dashboard", true)
                        .failureUrl("/management/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/management/logout")
                        .logoutSuccessUrl("/management/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 测试环境禁用CSRF

        return http.build();
    }
}