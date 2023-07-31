package com.goms.v2.global.security.jwt.common.exception

import com.goms.v2.gloabl.exception.ErrorCode
import com.goms.v2.gloabl.exception.exception.GomsException

class InvalidTokenException: GomsException(ErrorCode.INVALID_TOKEN)