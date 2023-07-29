package com.goms.v2.persistence.account.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository,
    private val accountMapper: AccountMapper
): AccountRepository {

    override fun save(account: Account) {
        val accountEntity = accountMapper.toEntity(account)
        accountJpaRepository.save(accountEntity)
    }

    override fun findByIdOrNull(accountIdx: UUID) =
        accountJpaRepository.findByIdOrNull(accountIdx)
            .let { accountMapper.toDomain(it) }

}