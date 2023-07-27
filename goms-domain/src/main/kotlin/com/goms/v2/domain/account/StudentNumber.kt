package com.goms.v2.domain.account

import com.goms.v2.common.annotation.Aggregate

@Aggregate
data class StudentNumber(
    val grade: Int,
    val classNum: Int,
    val number: Int
)