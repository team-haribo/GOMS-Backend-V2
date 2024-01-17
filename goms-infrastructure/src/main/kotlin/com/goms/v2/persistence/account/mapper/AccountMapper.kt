package com.goms.v2.persistence.account.mapper

import com.goms.v2.domain.account.Account
import com.goms.v2.persistence.account.entity.AccountJpaEntity
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun toDomain(entity: AccountJpaEntity?): Account? =
        entity?.let {
            Account(
                idx = entity.idx,
                phoneNumber = entity.phoneNumber,
                password = entity.password,
                grade = entity.grade,
                gender = entity.gender,
                name = entity.name,
                profileUrl = entity.profileUrl,
                authority = entity.authority,
                createdTime = entity.createdTime
            )
        }

    fun toEntity(domain: Account): AccountJpaEntity =
        AccountJpaEntity(
            idx = domain.idx,
            phoneNumber = domain.phoneNumber,
            password = domain.password,
            grade = domain.grade,
            gender = domain.gender,
            name = domain.name,
            profileUrl = domain.profileUrl,
            authority = domain.authority,
            createdTime = domain.createdTime
        )

}