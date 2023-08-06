package com.goms.v2.repository.outing

import com.goms.v2.domain.outing.OutingBlackList

interface OutingBlackListRepository {

    fun save(outingBlacklist: OutingBlackList)

}