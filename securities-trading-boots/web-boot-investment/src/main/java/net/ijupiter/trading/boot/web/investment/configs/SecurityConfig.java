package net.ijupiter.trading.boot.web.investment.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import net.ijupiter.trading.boot.web.investment.security.InvestmentUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final InvestmentUserDetailsService investmentUserDetailsService;

    public SecurityConfig(@Lazy InvestmentUserDetailsService investmentUserDetailsService) {
        this.investmentUserDetailsService = investmentUserDetailsService;
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/investment/login", "/webjars/**", "/static/**", "/error", "/common/**").permitAll()
                        .requestMatchers("/investment/**").authenticated()
                        .anyRequest().authenticated()
                )
                .userDetailsService(investmentUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/investment/login")
                        .defaultSuccessUrl("/investment/dashboard", true)
                        .failureUrl("/investment/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/investment/logout")
                        .logoutSuccessUrl("/investment/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 测试环境禁用CSRF

        return http.build();
    }
}