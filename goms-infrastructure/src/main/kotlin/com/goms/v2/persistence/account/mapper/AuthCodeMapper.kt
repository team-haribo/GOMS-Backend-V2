package com.goms.v2.persistence.account.mapper

import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.persistence.auth.entity.AuthCodeRedisEntity
import org.springframework.stereotype.Component

@Component
class AuthCodeMapper {

    fun toEntity(domain: AuthCode): AuthCodeRedisEntity =
        AuthCodeRedisEntity(
            email = domain.email,
            authCode = domain.authCode,
            expiredAt = domain.expiredAt
        )

    fun toDomain(entity: AuthCodeRedisEntity?): AuthCode? =
        entity?.let {
            AuthCode(
                email = it.email,
                authCode = it.authCode,
                expiredAt = it.expiredAt
            )
        }

}