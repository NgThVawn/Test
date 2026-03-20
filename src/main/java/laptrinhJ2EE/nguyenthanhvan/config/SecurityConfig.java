package laptrinhJ2EE.nguyenthanhvan.config;

import laptrinhJ2EE.nguyenthanhvan.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http
                .authorizeHttpRequests(authz -> authz
                    // Cho phép truy cập công khai
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()

                    // Chỉ ADMIN mới có quyền thêm, sửa, xóa
                    .requestMatchers("/sinhvien/add", "/sinhvien/edit/**", "/sinhvien/delete/**").hasRole("ADMIN")
                    .requestMatchers("/monhoc/add", "/monhoc/edit/**", "/monhoc/delete/**").hasRole("ADMIN")
                    .requestMatchers("/lop/add", "/lop/edit/**", "/lop/delete/**").hasRole("ADMIN")

                    // Các trang xem yêu cầu đăng nhập (cả ADMIN và SINHVIEN)
                    .requestMatchers("/sinhvien/**", "/monhoc/**", "/lop/**").authenticated()

                    // Các request khác cần xác thực
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .permitAll()
                )
                .exceptionHandling(exception -> exception
                    .accessDeniedPage("/access-denied")
                );

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



