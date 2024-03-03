package com.board.authserver.common.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@ConfigurationProperties(prefix = "authorization")
data class AuthorizationProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String
)