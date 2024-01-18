package com.goms.v2.persistence.late.mapper

import com.goms.v2.domain.late.Late
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.persistence.late.entity.LateJpaEntity
import org.springframework.stereotype.Component

@Component
class LateMapper(
    private val accountMapper: AccountMapper
) {

    fun toEntity(late: Late): LateJpaEntity =
        LateJpaEntity(
            idx = late.idx,
            account = accountMapper.toEntity(late.account),
            createdTime = late.createdTime
        )

    fun toDomain(lateJpaEntity: LateJpaEntity?): Late? =
        lateJpaEntity?.let {
            Late(
                idx = it.idx,
                account = accountMapper.toDomain(it.account)!!,
                createdTime = it.createdTime
            )
        }

}