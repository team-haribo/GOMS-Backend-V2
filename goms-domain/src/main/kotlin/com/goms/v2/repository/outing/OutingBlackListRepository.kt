package com.goms.v2.repository.outing

import com.goms.v2.domain.outing.OutingBlackList
import java.util.UUID

interface OutingBlackListRepository {

    fun save(outingBlacklist: OutingBlackList)
    fun deleteById(accountIdx: UUID)
    fun existsById(accountIdx: UUID): Boolean
    fun findAll(): List<OutingBlackList>

}