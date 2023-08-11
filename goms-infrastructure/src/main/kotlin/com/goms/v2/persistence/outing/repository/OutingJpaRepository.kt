package com.goms.v2.persistence.outing.repository

import com.goms.v2.persistence.account.entity.AccountJpaEntity
import com.goms.v2.persistence.outing.entity.OutingJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface OutingJpaRepository: CrudRepository<OutingJpaEntity, Long> {

    fun deleteByAccountIdx(accountIdx: UUID)
    fun existsByAccount(account: AccountJpaEntity): Boolean
    fun queryAllByOrderByCreatedTimeDesc(): List<OutingJpaEntity>

}