package com.goms.v2.global.security.jwt

import com.goms.v2.domain.account.Authority
import com.goms.v2.global.security.jwt.common.exception.InvalidTokenException
import com.goms.v2.global.security.jwt.common.properties.JwtProperties
import com.goms.v2.global.security.principal.StudentCouncilDetailsService
import com.goms.v2.global.security.principal.StudentDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import javax.servlet.http.HttpServletRequest

@Component
class JwtParser(
    private val studentDetailsService: StudentDetailsService,
    private val studentCouncilDetailsService: StudentCouncilDetailsService,
    private val jwtProperties: JwtProperties
) {

    fun parseAccessToken(request: HttpServletRequest): String? =
        request.getHeader(JwtProperties.TOKEN_HEADER)
            .let { it ?: return null }
            .let { if (it.startsWith(JwtProperties.TOKEN_PREFIX)) it.replace(JwtProperties.TOKEN_PREFIX, "") else null }

    fun parseRefreshToken(refreshToken: String): String? =
        if (refreshToken.startsWith(JwtProperties.TOKEN_PREFIX)) refreshToken.replace(JwtProperties.TOKEN_PREFIX, "") else null

    fun authentication(accessToken: String): Authentication =
        getAuthority(getTokenBody(accessToken, jwtProperties.accessSecret))
            .let { UsernamePasswordAuthenticationToken(it, "", it.authorities) }

    fun getTokenBody(token: String, secret: Key): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body

    private fun getAuthority(body: Claims): UserDetails =
        when (body.get(JwtProperties.AUTHORITY, String::class.java)) {
            Authority.ROLE_STUDENT.name -> studentDetailsService.loadUserByUsername(body.subject)
            Authority.ROLE_STUDENT_COUNCIL.name -> studentCouncilDetailsService.loadUserByUsername(body.subject)
            else -> throw InvalidTokenException()
        }

}