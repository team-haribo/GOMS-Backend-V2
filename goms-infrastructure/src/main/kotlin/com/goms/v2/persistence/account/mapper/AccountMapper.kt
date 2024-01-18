package com.goms.v2.persistence.account.mapper

import com.goms.v2.domain.account.Account
import com.goms.v2.persistence.account.entity.AccountJpaEntity
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun toDomain(accountJpaEntity: AccountJpaEntity?): Account? =
        accountJpaEntity?.let {
            Account(
                idx = it.idx,
                email = it.email,
                password = it.password,
                grade = it.grade,
                gender = it.gender,
                major = it.major,
                name = it.name,
                profileUrl = it.profileUrl,
                authority = it.authority,
                createdTime = it.createdTime
            )
        }

    fun toEntity(account: Account): AccountJpaEntity =
        AccountJpaEntity(
            idx = account.idx,
            email = account.email,
            password = account.password,
            grade = account.grade,
            gender = account.gender,
            major = account.major,
            name = account.name,
            profileUrl = account.profileUrl,
            authority = account.authority,
            createdTime = account.createdTime
        )

}