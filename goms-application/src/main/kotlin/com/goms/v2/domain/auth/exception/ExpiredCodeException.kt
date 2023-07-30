package com.goms.v2.domain.auth.exception

import com.goms.v2.common.error.GomsException
import com.goms.v2.domain.auth.exception.error.AuthErrorCode

object ExpiredCodeException: GomsException(
    AuthErrorCode.EXPIRED_CODE
)