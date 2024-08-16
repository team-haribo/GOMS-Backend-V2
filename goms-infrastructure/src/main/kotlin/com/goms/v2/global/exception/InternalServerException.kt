package com.goms.v2.global.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class InternalServerException: GomsException(ErrorCode.GOMS_SERVER_ERROR)