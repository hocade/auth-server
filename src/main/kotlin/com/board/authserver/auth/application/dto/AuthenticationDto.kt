package com.board.authserver.auth.application.dto

/**
 * @author jinwook.kim
 * @since 3/8/24
 * OAuth2Authorization class 바인딩을 위한 dto
 */
data class AuthenticationDto(
    val authorities: Collection<Map<String, String>>?,
    val credentials: Any?,
    val details: Any?,
    val name: String?,
    val principal: String?,
    val authenticated: Boolean
)