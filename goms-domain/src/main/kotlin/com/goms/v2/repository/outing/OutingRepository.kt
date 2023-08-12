package com.goms.v2.repository.outing

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.Outing
import java.util.UUID

interface OutingRepository {

    fun save(outing: Outing)
    fun deleteByAccountIdx(accountIdx: UUID)
    fun existsByAccount(account: Account): Boolean
    fun findAllByOrderByCreatedTimeDesc(): List<Outing>
    fun count(): Long
    fun findAll(): List<Outing>
    fun queryByAccountNameContaining(name: String): List<Outing>

}