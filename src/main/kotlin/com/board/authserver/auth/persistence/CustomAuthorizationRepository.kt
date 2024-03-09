package com.board.authserver.auth.persistence

import com.board.authserver.auth.domain.CustomAuthorization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

/**
 * @author jinwook.kim
 * @since 3/7/24
 */
interface CustomAuthorizationRepository : JpaRepository<CustomAuthorization, String> {
    fun findByState(state: String?): Optional<CustomAuthorization>
    fun findByAuthorizationCodeValue(authorizationCode: String?): Optional<CustomAuthorization>
    fun findByAccessTokenValue(accessToken: String?): Optional<CustomAuthorization>
    fun findByRefreshTokenValue(refreshToken: String?): Optional<CustomAuthorization>

    @Query(
        "select a from CustomAuthorization a where a.state = :token" +
                " or a.authorizationCodeValue = :token" +
                " or a.accessTokenValue = :token" +
                " or a.refreshTokenValue = :token"
    )
    fun findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(
        @Param("token") token: String?
    ): Optional<CustomAuthorization>

}