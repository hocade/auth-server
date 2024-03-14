package com.board.authserver.auth.application.mapper

import com.board.authserver.auth.domain.CustomAuthorization
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.factory.Mappers
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode
import org.springframework.util.StringUtils

/**
 * @author jinwook.kim
 * @since 3/14/24
 * todo : Entity 필드가 전부 var 로 변경되어 setter 제한 필요
 */
@Mapper(componentModel = "spring")
abstract class CustomAuthorizationMapper {
    companion object {
        val instance: CustomAuthorizationMapper = Mappers.getMapper(CustomAuthorizationMapper::class.java)
    }
    @Mapping(target = "id", source = "authorization.id")
    @Mapping(target = "principalName", source = "authorization.principalName")
    @Mapping(target = "attributes", source = "attributes")
    @Mapping(target = "registeredClientId", source = "authorization.registeredClientId")
    @Mapping(target = "authorizationGrantType", source = "authorization.authorizationGrantType.value")
    @Mapping(target = "state", source = "authorization", qualifiedByName = ["state"])
    @Mapping(target = "authorizationCodeValue", source = "authorizationCode.token.tokenValue")
    @Mapping(target = "authorizationCodeIssuedAt", source = "authorizationCode.token.issuedAt")
    @Mapping(target = "authorizationCodeExpiresAt", source = "authorizationCode.token.expiresAt")
    @Mapping(target = "accessTokenValue", source = "accessToken.token.tokenValue")
    @Mapping(target = "accessTokenIssuedAt", source = "accessToken.token.issuedAt")
    @Mapping(target = "accessTokenExpiresAt", source = "accessToken.token.expiresAt")
    @Mapping(target = "accessTokenScopes", source = "accessToken", qualifiedByName = ["accessTokenScopes"])
    @Mapping(target = "refreshTokenValue", source = "refreshToken.token.tokenValue")
    @Mapping(target = "refreshTokenIssuedAt", source = "refreshToken.token.issuedAt")
    @Mapping(target = "refreshTokenExpiresAt", source = "refreshToken.token.expiresAt")
    abstract fun toEntity(
        authorization: OAuth2Authorization,
        authorizationCode: OAuth2Authorization.Token<OAuth2AuthorizationCode>?,
        accessToken: OAuth2Authorization.Token<OAuth2AccessToken>?,
        refreshToken: OAuth2Authorization.Token<OAuth2RefreshToken>?,
        attributes: String?
    ): CustomAuthorization

    @Named("state")
    fun state(authorization: OAuth2Authorization): String? {
        return authorization.getAttribute(OAuth2ParameterNames.STATE)
    }

    @Named("accessTokenScopes")
    fun accessTokenScopes(accessToken: OAuth2Authorization.Token<OAuth2AccessToken>?): String? {
        return StringUtils.collectionToDelimitedString(accessToken?.token?.scopes, ",")
    }

}