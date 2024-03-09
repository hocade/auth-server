package com.board.authserver.auth.application

import com.board.authserver.auth.application.dto.AuthenticationDto
import com.board.authserver.auth.application.dto.OAuth2AuthorizationRequestDto
import com.board.authserver.auth.domain.CustomAuthorization
import com.board.authserver.auth.persistence.CustomAuthorizationRepository
import com.board.authserver.auth.persistence.CustomRegisteredClientRepository
import com.board.authserver.common.exception.CommonException
import com.board.authserver.common.exception.enum.CommonExceptionCode
import com.board.authserver.common.util.ReflectionUtil
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.Principal
import java.util.*

/**
 * @author jinwook.kim
 * @since 3/6/24
 * Oauth2 인증, 인가 정보를 db에 저장하기 위한 custom class
 * todo : redis 에 저장
 */
@Component
class CustomOAuth2AuthorizationService(
    private val authorizationRepository: CustomAuthorizationRepository,
    private val customClientRepository: CustomRegisteredClientRepository,
    private val objectMapper: ObjectMapper
) : OAuth2AuthorizationService {

    override fun save(authorization: OAuth2Authorization) {
        authorizationRepository.save(toEntity(authorization))
    }

    override fun remove(authorization: OAuth2Authorization) {
        authorizationRepository.deleteById(authorization.id)
    }

    override fun findById(id: String): OAuth2Authorization {
        return authorizationRepository.findById(id).map(this::toObject).orElse(null);
    }

    override fun findByToken(token: String?, tokenType: OAuth2TokenType?): OAuth2Authorization? {
        if (tokenType == null) {
            return authorizationRepository.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(token)
                .map(::toObject).orElse(null)
        }
        val result: Optional<CustomAuthorization> = when (tokenType.value) {
            OAuth2ParameterNames.STATE -> authorizationRepository.findByState(token!!)
            OAuth2ParameterNames.CODE -> authorizationRepository.findByAuthorizationCodeValue(token)
            OAuth2ParameterNames.ACCESS_TOKEN -> authorizationRepository.findByAccessTokenValue(token)
            OAuth2ParameterNames.REFRESH_TOKEN -> authorizationRepository.findByRefreshTokenValue(token)
            else -> Optional.empty()
        }
        return result.map(::toObject).orElse(null)
    }

    /**
     * 저장된 CustomAuthorization class를 인증, 인가에 사용하기 위해 spring security 에서 사용하는 OAuth2Authorization class 로 변환
     */
    private fun toObject(entity: CustomAuthorization): OAuth2Authorization {
        val registeredClient: RegisteredClient = customClientRepository.findById(entity.registeredClientId) ?: throw CommonException(CommonExceptionCode.CLIENT_NOT_FOUND)

        val builder = OAuth2Authorization.withRegisteredClient(registeredClient)
            .id(entity.id)
            .principalName(entity.principalName)
            .authorizationGrantType(AuthorizationGrantType(entity.authorizationGrantType))
            .authorizedScopes(StringUtils.commaDelimitedListToSet(entity.accessTokenScopes))
            .attributes { setAttributes(entity, it) }

        if (entity.state != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.state)
        }

        if (entity.authorizationCodeValue != null) {
            val authorizationCode = OAuth2AuthorizationCode(
                entity.authorizationCodeValue,
                entity.authorizationCodeIssuedAt,
                entity.authorizationCodeExpiresAt
            )
            builder.token(authorizationCode)
        }

        if (entity.accessTokenValue != null) {
            val accessToken = OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                entity.accessTokenValue,
                entity.accessTokenIssuedAt,
                entity.accessTokenExpiresAt,
                StringUtils.commaDelimitedListToSet(entity.accessTokenScopes)
            )
            builder.token(accessToken)
        }

        if (entity.refreshTokenValue != null) {
            val refreshToken = OAuth2RefreshToken(
                entity.refreshTokenValue,
                entity.refreshTokenIssuedAt,
                entity.refreshTokenExpiresAt
            )
            builder.token(refreshToken)
        }

        return builder.build()
    }

    /**
     * Spring security 에서 제공하는 OAuth2Authorization 정보를 db에 저장 하기 위해 CustomAuthorization class로 바인딩
     */
    private fun toEntity(authorization: OAuth2Authorization): CustomAuthorization {
        val authorizationCode = authorization.getToken(
            OAuth2AuthorizationCode::class.java
        )

        val accessToken = authorization.getToken(
            OAuth2AccessToken::class.java
        )

        val refreshToken = authorization.getToken(
            OAuth2RefreshToken::class.java
        )

        val entity = CustomAuthorization(
            authorization.id, authorization.principalName, writeMap(authorization.attributes), authorization.registeredClientId, authorization.authorizationGrantType.value, authorization.getAttribute(OAuth2ParameterNames.STATE),
            authorizationCode?.token?.tokenValue, authorizationCode?.token?.issuedAt , authorizationCode?.token?.expiresAt, accessToken?.token?.tokenValue, accessToken?.token?.issuedAt, accessToken?.token?.expiresAt, StringUtils.collectionToDelimitedString(accessToken?.token?.scopes, ","),  refreshToken?.token?.tokenValue, refreshToken?.token?.issuedAt, refreshToken?.token?.expiresAt
            )

        return entity
    }

    private fun setAttributes(entity: CustomAuthorization, attributes: MutableMap<String, Any>) {
        parseMap(entity.attributes)?.let {
            it.entries.forEach {
                when (it.key) {
                    OAuth2AuthorizationRequest::class.java.name -> {
                        val dto = ReflectionUtil.mapToObject(it.value as Map<String, Any>, OAuth2AuthorizationRequestDto::class)
                        attributes.put(it.key!!, OAuth2AuthorizationRequest.authorizationCode()
                            .authorizationRequestUri(dto.authorizationRequestUri)
                            .authorizationUri(dto.authorizationUri)
                            .clientId(dto.clientId)
                            .redirectUri(dto.redirectUri)
                            .build())
                    }
                    Principal::class.java.name -> {
                        val dto = ReflectionUtil.mapToObject(it.value as Map<String, Any>, AuthenticationDto::class)
                        val authorities: MutableList<GrantedAuthority> = mutableListOf()
                        dto.authorities?.forEach{
                            authorities.addAll(it.values.stream().map { SimpleGrantedAuthority(it) }.toList())
                        }
                        attributes.put(it.key!!, UsernamePasswordAuthenticationToken.authenticated(dto.principal, dto.credentials, authorities))
                    }
                }
            }
        }
    }

    private fun writeMap(metadata: Map<String, Any>): String? {
        return try {
            objectMapper.writeValueAsString(metadata)
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message, ex)
        }
    }

    private fun parseMap(data: String?): Map<String?, Any?>? {
        return try {
            objectMapper.readValue(data, object : TypeReference<Map<String?, Any?>?>() {})
        } catch (ex: java.lang.Exception) {
            throw IllegalArgumentException(ex.message, ex)
        }
    }

}