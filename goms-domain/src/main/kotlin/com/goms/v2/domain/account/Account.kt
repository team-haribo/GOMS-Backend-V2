package com.goms.v2.domain.account

import com.goms.v2.common.annotation.RootAggregate
import java.time.LocalDateTime
import java.util.*

@RootAggregate
data class Account(
    val idx: UUID,
    val email: String,
    val studentNumber: StudentNumber,
    val name: String,
    val profileUrl: String?,
    var authority: Authority,
    val createdTime: LocalDateTime
)

fun Account.updateAuthority(newAuthority: Authority) {
    authority = newAuthority
}