package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class NotificationType {

    BEFORE_OUTING, AFTER_OUTING

}