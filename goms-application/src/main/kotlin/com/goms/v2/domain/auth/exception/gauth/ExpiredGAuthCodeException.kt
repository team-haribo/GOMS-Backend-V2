package com.goms.v2.domain.auth.exception.gauth

import com.goms.v2.gloabl.exception.ErrorCode
import com.goms.v2.gloabl.exception.exception.GomsException

class ExpiredGAuthCodeException: GomsException(ErrorCode.EXPIRED_GAUTH_CODE)