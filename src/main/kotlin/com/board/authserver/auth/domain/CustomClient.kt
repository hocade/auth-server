package com.board.authserver.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

/**
 * @author jinwook.kim
 * @since 3/7/24
 */
@Entity
@Table(name = "OAUTH2_REGISTERED_CLIENT")
class CustomClient(
    @Id
    val id: String,

    @Column(name = "client_id")
    val clientId: String,
    @Column(name = "client_secret")
    val clientSecret: String,
    @Column(name = "client_secret_expires_at")
    val clientSecretExpiresAt: Instant,
    @Column(name = "client_name")
    val clientName: String,
    @Column(name = "authorization_grant_types")
    val authorizationGrantTypes: String,
    @Column(name = "redirect_uris")
    val redirectUris: String,
    @Column(name = "scopes")
    val scopes: String,
    @Column(name = "client_authentication_methods")
    val clientAuthenticationMethods: String,
)