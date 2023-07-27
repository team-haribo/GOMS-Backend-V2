package com.goms.v2.repository

import com.goms.v2.domain.account.Account

interface AccountRepository {

    fun save(account: Account)

}