package com.goms.v2.repository.account

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.util.UUID

interface AccountRepository {

    fun save(account: Account): Account
    fun findByIdOrNull(accountIdx: UUID): Account?
    fun findByEmail(email: String): Account?
    fun existsByEmail(email: String): Boolean
    fun findAllOrderByStudentNum(): List<Account>
    fun findAccountByStudentInfo(
        grade: Int?,
        gender: Gender?,
        name: String?,
        authority: Authority?,
        major: Major?
    ): List<Account>
    fun findAll(): List<Account>

    fun delete(account: Account)

}
