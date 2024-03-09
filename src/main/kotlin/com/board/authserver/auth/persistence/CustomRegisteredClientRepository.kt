package com.board.authserver.auth.persistence

import com.board.authserver.auth.domain.CustomClient
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils


/**
 * @author jinwook.kim
 * @since 3/7/24
 * Oauth2 Client를 db에 저장하기 위한 custom class
 */
@Component
class CustomRegisteredClientRepository(
    private val clientRepository: CustomClientRepository
) : RegisteredClientRepository {

    override fun save(registeredClient: RegisteredClient) {
        clientRepository.save(toEntity(registeredClient))
    }

    override fun findById(id: String): RegisteredClient? {
        return clientRepository.findById(id).map(this::toObject).orElse(null)
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        return clientRepository.findByClientId(clientId).map(this::toObject).orElse(null)
    }

    /**
     * spring security에서 사용되는 class로 변환
     */
    private fun toObject(client: CustomClient): RegisteredClient? {
        val clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(client.clientAuthenticationMethods)
        val authorizationGrantTypes = StringUtils.commaDelimitedListToSet(client.authorizationGrantTypes)
        val redirectUris = StringUtils.commaDelimitedListToSet(client.redirectUris)
        val clientScopes = StringUtils.commaDelimitedListToSet(client.scopes)

        val builder = RegisteredClient.withId(client.id)
            .clientId(client.clientId)
            .clientSecret(client.clientSecret)
            .clientSecretExpiresAt(client.clientSecretExpiresAt)
            .clientName(client.clientName)
            .clientAuthenticationMethods { authenticationMethods ->
                clientAuthenticationMethods.forEach{ customClientAuthenticationMethods ->
                    authenticationMethods.add(ClientAuthenticationMethod(customClientAuthenticationMethods))
                }
            }
            .authorizationGrantTypes { grantTypes ->
                authorizationGrantTypes.forEach{ grantTypes.add(AuthorizationGrantType(it)) }
            }
            .redirectUris { it.addAll(redirectUris) }
            .scopes { it.addAll(clientScopes) }

        return builder.build()
    }

    /**
     * db 저장을 위한 entity 생성
     */
    private fun toEntity(registeredClient: RegisteredClient): CustomClient {
        val clientAuthenticationMethods: MutableList<String> = ArrayList(registeredClient.clientAuthenticationMethods.size)
        registeredClient.clientAuthenticationMethods.forEach{ clientAuthenticationMethods.add(it.value) }

        val authorizationGrantTypes: MutableList<String> = ArrayList(registeredClient.authorizationGrantTypes.size)
        registeredClient.authorizationGrantTypes.forEach{ authorizationGrantTypes.add(it.value) }

        val entity = CustomClient(
            registeredClient.id, registeredClient.clientId, registeredClient.clientSecret!!, registeredClient.clientSecretExpiresAt!!, registeredClient.clientName, StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes),
            StringUtils.collectionToCommaDelimitedString(registeredClient.redirectUris), StringUtils.collectionToCommaDelimitedString(registeredClient.scopes), StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods)
        )
        return entity
    }

}