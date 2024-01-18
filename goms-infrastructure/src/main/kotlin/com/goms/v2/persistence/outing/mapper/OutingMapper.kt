package com.goms.v2.persistence.outing.mapper

import com.goms.v2.domain.outing.Outing
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.persistence.outing.entity.OutingJpaEntity
import org.springframework.stereotype.Component

@Component
class OutingMapper(
    private val accountMapper: AccountMapper
) {

    fun toEntity(outing: Outing) =
        OutingJpaEntity(
            idx = outing.idx,
            account = accountMapper.toEntity(outing.account),
            createdTime = outing.createdTime
        )

    fun toDomain(outingJpaEntity: OutingJpaEntity) =
        Outing(
            idx = outingJpaEntity.idx,
            account = accountMapper.toDomain(outingJpaEntity.account)!!,
            createdTime = outingJpaEntity.createdTime
        )


}