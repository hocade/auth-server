package com.board.authserver.auth.application.dto

/**
 * @author jinwook.kim
 * @since 3/8/24
 * OAuth2AuthorizationRequest class 바인딩을 위한 dto
 */
data class OAuth2AuthorizationRequestDto(
    val authorizationUri: String?,
    val responseType: Map<String, Any>?,
    val clientId: String?,
    val redirectUri: String?,
    val scopes: List<Any>?,
    val state: String?,
    val additionalParameters: Map<String, Any>?,
    val authorizationRequestUri: String?,
    val attributes: Map<String, Any>?,
    val grantType: Map<String, Any>?
)