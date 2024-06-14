package com.goms.v2.domain.notification.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class DeviceTokenNotFoundException: GomsException(ErrorCode.DEVICE_TOKEN_NOT_FOUND)