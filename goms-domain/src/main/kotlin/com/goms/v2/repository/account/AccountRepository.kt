package com.goms.v2.repository.account

import com.goms.v2.domain.account.Account
import java.util.UUID

interface AccountRepository {

    fun save(account: Account)

    fun findByIdOrNull(accountIdx: UUID): Account?

    fun findByEmail(email: String): Account?

}