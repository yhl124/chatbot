package com.example.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
       	
        	.csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")) // /api/** 경로에 대해 CSRF 비활성화 
                .ignoringRequestMatchers(new AntPathRequestMatcher("/chat/**"))
            )
//        	.csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll() // /api/** 경로에 대해 접근 허용
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // 나머지 경로에 대해 접근 허용
            )
            .formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll()
            )
            .sessionManagement(sessionManagement -> sessionManagement
                    .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession) // 세션 고정 보호 활성화
                    .maximumSessions(1) // 동시 세션의 최대 수 설정
                    .maxSessionsPreventsLogin(false) // 최대 세션 수 초과 시 이전 세션 만료
            )
            ;
        return http.build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
