package com.board.authserver.common.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author jinwook.kim
 * @since 2/1/24
 */
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    val expirationHours: Long
)