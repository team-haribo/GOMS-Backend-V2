package com.goms.v2.persistence.account.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.repository.AccountRepository
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository,
): AccountRepository {

    override fun save(account: Account) {
        val accountEntity = AccountMapper.INSTANCE.toEntity(account)
        accountJpaRepository.save(accountEntity)
    }

}