package com.goms.v2.domain.email.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class TooManyAuthCodeRequestException: GomsException(ErrorCode.TOO_MANY_AUTH_CODE_REQUEST)