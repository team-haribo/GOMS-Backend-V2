package com.goms.v2.global.security.jwt

import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.global.security.jwt.common.properties.JwtExpTimeProperties
import com.goms.v2.global.security.jwt.common.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateTokenAdapter(
    private val jwtProperties: JwtProperties,
    private val jwtExpTimeProperties: JwtExpTimeProperties
): TokenPort {

    override fun generateToken(accountIdx: UUID, authority: Authority): TokenDto =
        TokenDto(
            accessToken = generateAccessToken(accountIdx, authority),
            refreshToken = generateRefreshToken(accountIdx),
            accessTokenExp = jwtExpTimeProperties.accessExp,
            refreshTokenExp = jwtExpTimeProperties.refreshExp,
            authority = authority
        )

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