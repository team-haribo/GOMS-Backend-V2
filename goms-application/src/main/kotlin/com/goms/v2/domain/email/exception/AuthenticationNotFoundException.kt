package com.goms.v2.domain.email.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class AuthenticationNotFoundException: GomsException(ErrorCode.AUTHENTICATION_NOT_FOUND)