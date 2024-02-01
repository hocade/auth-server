package com.board.authserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain

/**
 * @author jinwook.kim
 * @since 2/1/24
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {
    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/v3/**", "/sign-up", "/sign-in")

    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        return http
            .csrf().disable()
            .headers { it.frameOptions().sameOrigin() }
            .authorizeHttpRequests {
                it.requestMatchers(*allowedUrls).permitAll()    // 허용할 url 목록
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()!!
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}