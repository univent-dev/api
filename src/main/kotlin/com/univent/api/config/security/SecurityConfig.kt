package com.univent.api.config.security

import com.univent.api.iam.JwtApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtApi: JwtApi
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // 기본 설정
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        // 경로별 권한 설정
        http.authorizeHttpRequests {
            it.anyRequest().permitAll()
        }

        // JWT 인증 필터 추가
        http.addFilterBefore(
            JwtAuthenticationFilter(jwtApi),
            UsernamePasswordAuthenticationFilter::class.java
        )

        // OAuth2 로그인 설정
        http.oauth2Login {  }

        // 예외 처리 설정
        http.exceptionHandling {  }

        return http.build()
    }
}
