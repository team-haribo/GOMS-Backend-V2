package com.goms.v2.domain.auth.exception.gauth

import com.goms.v2.gloabl.exception.ErrorCode
import com.goms.v2.gloabl.exception.exception.GomsException

class GAuthServiceNotFoundException: GomsException(ErrorCode.GAUTH_SERVICE_NOT_FOUND)