package com.board.authserver.config

import com.board.authserver.config.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.spec.SecretKeySpec

/**
 * @author jinwook.kim
 * @since 2/1/24
 */
@Service
class TokenProvider(
    private val jwtProperties: JwtProperties
) {

    fun createToken(userSpecification: String): String{
        return Jwts.builder()
            .signWith(SecretKeySpec(jwtProperties.secretKey.toByteArray(), SignatureAlgorithm.HS512.jcaName)) // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
            .setSubject(userSpecification)   // JWT 토큰 제목
            .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.expirationHours, ChronoUnit.HOURS)))    // JWT 토큰의 만료시간 설정
            .compact()!!    // JWT 토큰 생성
    }

    fun validateTokenAndGetSubject(token: String): String?{
        return Jwts.parserBuilder()
            .setSigningKey(jwtProperties.secretKey.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

}