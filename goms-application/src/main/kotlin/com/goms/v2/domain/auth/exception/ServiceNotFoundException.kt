package com.goms.v2.domain.auth.exception

import com.goms.v2.gloabl.exception.ErrorCode
import com.goms.v2.gloabl.exception.exception.GomsException

class ServiceNotFoundException: GomsException(ErrorCode.GAUTH_SERVICE_NOT_FOUND)