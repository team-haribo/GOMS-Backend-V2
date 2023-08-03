package com.goms.v2.global.security.jwt

import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.global.security.jwt.common.properties.JwtExpTimeProperties
import com.goms.v2.global.security.jwt.common.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class GenerateTokenAdapter(
    private val jwtProperties: JwtProperties,
    private val jwtParser: JwtParser,
    private val jwtExpTimeProperties: JwtExpTimeProperties
): TokenPort {

    override fun generateToken(accountIdx: UUID, authority: Authority): TokenDto =
        TokenDto(
            accessToken = generateAccessToken(accountIdx, authority),
            refreshToken = generateRefreshToken(accountIdx),
            accessTokenExp = LocalDateTime.now().plusSeconds(jwtExpTimeProperties.accessExp.toLong()),
            refreshTokenExp = LocalDateTime.now().plusSeconds(jwtExpTimeProperties.refreshExp.toLong()),
            authority = authority
        )

    override fun parseRefreshToken(refreshToken: String): String? =
        jwtParser.parseRefreshToken(refreshToken)

    private fun generateAccessToken(accountIdx: UUID, authority: Authority): String =
        Jwts.builder()
            .signWith(jwtProperties.accessSecret, SignatureAlgorithm.HS256)
            .setSubject(accountIdx.toString())
            .claim(JwtProperties.TOKEN_TYPE, JwtProperties.ACCESS)
            .claim(JwtProperties.AUTHORITY, authority)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpTimeProperties.accessExp * 1000))
            .compact()

    private fun generateRefreshToken(accountIdx: UUID): String =
        Jwts.builder()
            .signWith(jwtProperties.refreshSecret, SignatureAlgorithm.HS256)
            .setSubject(accountIdx.toString())
            .claim(JwtProperties.TOKEN_TYPE, JwtProperties.REFRESH)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpTimeProperties.refreshExp * 1000))
            .compact()

}