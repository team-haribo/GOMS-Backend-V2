package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.persistence.auth.entity.RefreshTokenJpaEntity
import org.mapstruct.*
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh
import org.springframework.stereotype.Component
import kotlin.math.exp

@Component
class RefreshTokenMapper {

    fun toEntity(refreshToken: RefreshToken) =
        RefreshTokenJpaEntity(
            refreshToken = refreshToken.refreshToken,
            accountIdx = refreshToken.accountIdx,
            expiredAt = refreshToken.expiredAt
        )
    fun toDomain(refreshTokenJpaEntity: RefreshTokenJpaEntity?) =
        refreshTokenJpaEntity?.let {
            RefreshToken(
                refreshToken = it.refreshToken,
                accountIdx = it.accountIdx,
                expiredAt = it.expiredAt
            )
        }

}