package com.goms.v2.domain.outing.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class OutingUUIDUnverifiedException: GomsException(ErrorCode.OUTING_UUID_UNVERIFIED)