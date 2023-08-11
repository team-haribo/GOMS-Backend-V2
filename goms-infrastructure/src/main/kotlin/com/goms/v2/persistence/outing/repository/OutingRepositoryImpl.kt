package com.goms.v2.persistence.outing.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.Outing
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.persistence.outing.mapper.OutingMapper
import com.goms.v2.repository.outing.OutingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OutingRepositoryImpl(
    private val outingJpaRepository: OutingJpaRepository,
    private val outingMapper: OutingMapper,
    private val accountMapper: AccountMapper
): OutingRepository {

    override fun save(outing: Outing) {
        val outingEntity = outingMapper.toEntity(outing)
        outingJpaRepository.save(outingEntity)
    }

    override fun deleteByAccountIdx(accountIdx: UUID) {
        outingJpaRepository.deleteByAccountIdx(accountIdx)
    }

    override fun existsByAccount(account: Account): Boolean {
        val accountEntity = accountMapper.toEntity(account)
        return outingJpaRepository.existsByAccount(accountEntity)
    }

    override fun queryAllByOrderByCreatedTimeDesc(): List<Outing> =
        outingJpaRepository.queryAllByOrderByCreatedTimeDesc()
            .map { outingMapper.toDomain(it) }

}