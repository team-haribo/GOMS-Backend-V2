package com.goms.v2.repository.account

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.Authority
import java.util.UUID

interface AccountRepository {

    fun save(account: Account): Account
    fun findByIdOrNull(accountIdx: UUID): Account?
    fun findByEmail(email: String): Account?
    fun existsByEmail(email: String): Boolean
    fun findAllOrderByStudentNum(): List<Account>
    fun findAccountByStudentInfo(
        grade: Int?,
        classNum: Int?,
        name: String?,
        authority: Authority?
    ): List<Account>

}