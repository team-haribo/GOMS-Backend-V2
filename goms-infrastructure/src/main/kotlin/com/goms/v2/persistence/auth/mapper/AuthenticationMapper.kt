package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.Authentication
import com.goms.v2.persistence.auth.entity.AuthenticationRedisEntity
import org.springframework.stereotype.Component

@Component
class AuthenticationMapper {

    fun toDomain(entity: AuthenticationRedisEntity?): Authentication? =
        entity?.let {
            Authentication(
                email = entity.email,
                attemptCount = entity.attemptCount,
                authCodeCount = entity.authCodeCount,
                isAuthentication = entity.isAuthentication,
                expiredAt = entity.expiredAt
            )
        }

    fun toEntity(domain: Authentication): AuthenticationRedisEntity =
        AuthenticationRedisEntity(
            email = domain.email,
            attemptCount = domain.attemptCount,
            authCodeCount = domain.authCodeCount,
            isAuthentication = domain.isAuthentication,
            expiredAt = domain.expiredAt
        )

}