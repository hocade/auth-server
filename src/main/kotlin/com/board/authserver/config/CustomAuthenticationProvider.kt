package com.board.authserver.config

import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@Component
class CustomAuthenticationProvider (
    private val customUserDetailsService: UserDetailsService,
    private val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationProvider {

    @Transactional
    override fun authenticate(authentication: Authentication?): Authentication {
        return authentication?.let {
            val username = it.name as String
            val password = it.credentials as String

            val user = customUserDetailsService.loadUserByUsername(username)

            if(!passwordEncoder.matches(password, user.password)) {
                throw Exception()
            }

            if(!user.isEnabled) {
                throw Exception()
            }

            UsernamePasswordAuthenticationToken(username, password, user.authorities)
        }?: throw Exception()
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java
            .isAssignableFrom(authentication)
    }
}