package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class NotificationType {
    FIRST_NOTIFICATION, FINAL_NOTIFICATION
}