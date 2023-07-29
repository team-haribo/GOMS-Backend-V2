package com.goms.v2.repository

import com.goms.v2.domain.account.Account
import java.util.UUID

interface AccountRepository {

    fun save(account: Account)

    fun findByIdOrNull(uuid: UUID): Account?

}