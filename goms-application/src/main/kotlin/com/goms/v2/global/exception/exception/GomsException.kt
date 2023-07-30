package com.goms.v2.global.exception.exception

import com.goms.v2.global.exception.ErrorCode

open class GomsException(val errorCode: ErrorCode): RuntimeException(errorCode.message)