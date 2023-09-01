package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class Notification(
    val title: String,
    val content: String,
    val writer: Writer
)