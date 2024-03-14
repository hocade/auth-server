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
    var id: String,

    @Column(name = "principal_name")
    var principalName: String,

    @Column(name = "attributes")
    var attributes: String?,

    @Column(name = "registered_client_id")
    var registeredClientId: String,

    @Column(name = "authorization_grant_type")
    var authorizationGrantType: String,

    @Column(name = "state")
    var state: String?,

    @Column(name = "authorization_code_value")
    var authorizationCodeValue: String?,

    @Column(name = "authorization_code_issued_at")
    var authorizationCodeIssuedAt: Instant?,

    @Column(name = "authorization_code_expires_at")
    var authorizationCodeExpiresAt: Instant?,

    @Column(name = "access_token_value")
    var accessTokenValue: String?,

    @Column(name = "access_token_issued_at")
    var accessTokenIssuedAt: Instant?,

    @Column(name = "access_token_expires_at")
    var accessTokenExpiresAt: Instant?,

    @Column(name = "access_token_scopes")
    var accessTokenScopes: String?,

    @Column(name = "refresh_token_value")
    var refreshTokenValue: String?,

    @Column(name = "refresh_token_issued_at")
    var refreshTokenIssuedAt: Instant?,

    @Column(name = "refresh_token_expires_at")
    var refreshTokenExpiresAt: Instant?
)