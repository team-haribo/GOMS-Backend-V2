package com.goms.v2.persistence.outing.mapper

import com.goms.v2.domain.outing.OutingBlackList
import com.goms.v2.persistence.outing.entity.OutingBlackListRedisEntity
import org.springframework.stereotype.Component

@Component
class OutingBlackListMapper {

    fun toEntity(outingBlacklist: OutingBlackList) =
        OutingBlackListRedisEntity(
            accountIdx = outingBlacklist.accountIdx,
            expiredAt = outingBlacklist.expiredAt
        )

    fun toDomain(outingBlackListRedisEntity: OutingBlackListRedisEntity) =
        OutingBlackList(
            accountIdx = outingBlackListRedisEntity.accountIdx,
            expiredAt = outingBlackListRedisEntity.expiredAt
        )

}