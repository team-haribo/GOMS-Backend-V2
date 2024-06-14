package com.goms.v2.global.security.jwt.common.properties

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.nio.charset.StandardCharsets
import java.security.Key

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    accessSecret: String,
    refreshSecret: String
) {

    val accessSecret: Key = Keys.hmacShaKeyFor(accessSecret.toByteArray(StandardCharsets.UTF_8))
    val refreshSecret: Key = Keys.hmacShaKeyFor(refreshSecret.toByteArray(StandardCharsets.UTF_8))

    companion object {
        const val ACCESS = "access"
        const val REFRESH = "refresh"
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_HEADER = "Authorization"
        const val AUTHORITY = "authority"
        const val TOKEN_TYPE = "tokenType"
    }
}