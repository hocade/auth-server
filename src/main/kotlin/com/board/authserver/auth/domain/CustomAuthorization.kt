package com.board.authserver.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

/**
 * @author jinwook.kim
 * @since 3/7/24
 * Oauth2 인증, 인가 정보 저장 테이블
 */
@Entity
@Table(name = "OAUTH2_AUTHORIZATION")
class CustomAuthorization(
    @Id
    val id: String,

    @Column(name = "principal_name")
    val principalName: String,

    @Column(name = "attributes")
    val attributes: String?,

    @Column(name = "registered_client_id")
    val registeredClientId: String,

    @Column(name = "authorization_grant_type")
    val authorizationGrantType: String,

    @Column(name = "state")
    val state: String?,

    @Column(name = "authorization_code_value")
    val authorizationCodeValue: String?,

    @Column(name = "authorization_code_issued_at")
    val authorizationCodeIssuedAt: Instant?,

    @Column(name = "authorization_code_expires_at")
    val authorizationCodeExpiresAt: Instant?,

    @Column(name = "access_token_value")
    val accessTokenValue: String?,

    @Column(name = "access_token_issued_at")
    val accessTokenIssuedAt: Instant?,

    @Column(name = "access_token_expires_at")
    val accessTokenExpiresAt: Instant?,

    @Column(name = "access_token_scopes")
    val accessTokenScopes: String?,

    @Column(name = "refresh_token_value")
    val refreshTokenValue: String?,

    @Column(name = "refresh_token_issued_at")
    val refreshTokenIssuedAt: Instant?,

    @Column(name = "refresh_token_expires_at")
    val refreshTokenExpiresAt: Instant?
) {
}