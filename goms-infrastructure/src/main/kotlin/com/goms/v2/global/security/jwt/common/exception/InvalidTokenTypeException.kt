package com.goms.v2.global.security.jwt.common.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class InvalidTokenTypeException: GomsException(ErrorCode.INVALID_TOKEN_TYPE)