package com.goms.v2.domain.auth.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class AuthCodeNotMatchException: GomsException(ErrorCode.AUTH_CODE_NOT_MATCH)