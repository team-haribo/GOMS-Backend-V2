package com.goms.v2.domain.outing.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class BlackListNotAllowOutingException: GomsException(ErrorCode.BLACKLIST_NOT_ALLOW_OUTING)