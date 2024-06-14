package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class Writer(
    val writerName: String
) {

    GOMS("goms"),
    STUDENT_COUNCIL("학생회");

}