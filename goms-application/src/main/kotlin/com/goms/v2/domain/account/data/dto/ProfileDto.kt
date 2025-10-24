package com.goms.v2.domain.account.data.dto

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major

data class ProfileDto(
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Long,
    val outing: Boolean,
    val blackList: Boolean
) {
    constructor() : this(
        "",
        6,
        Major.SMART_IOT,
        Gender.MAN,
        Authority.ROLE_STUDENT_COUNCIL,
        null,
        0L,
        false,
        false
    )
}
