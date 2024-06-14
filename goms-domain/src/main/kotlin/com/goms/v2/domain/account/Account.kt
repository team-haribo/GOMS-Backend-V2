package com.goms.v2.domain.account

import com.goms.v2.common.annotation.RootAggregate
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.time.LocalDateTime
import java.util.*

@RootAggregate
data class Account(
    val idx: UUID,
    val email: String,
    var password: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val name: String,
    var profileUrl: String?,
    var authority: Authority,
    val createdTime: LocalDateTime
)

fun Account.updateAuthority(newAuthority: Authority) {
    authority = newAuthority
}

fun Account.updatePassword(newPassword: String) {
    password = newPassword
}

fun Account.updateProfileUrl(newProfileUrl: String) {
    profileUrl = newProfileUrl
}

fun Account.resetProfileUrl(deleteProfileUrl: String?) {
    profileUrl = deleteProfileUrl
}