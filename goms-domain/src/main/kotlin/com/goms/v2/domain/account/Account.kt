package com.goms.v2.domain.account

import com.goms.v2.common.annotation.RootAggregate
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import java.time.LocalDateTime
import java.util.*

@RootAggregate
data class Account(
    val idx: UUID,
    val phoneNumber: String,
    val password: String,
    val grade: Int,
    val gender: Gender,
    val name: String,
    val profileUrl: String?,
    var authority: Authority,
    val createdTime: LocalDateTime
)

fun Account.updateAuthority(newAuthority: Authority) {
    authority = newAuthority
}