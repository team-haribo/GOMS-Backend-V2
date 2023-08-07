package com.goms.v2.common.exception

open class GomsException(val errorCode: ErrorCode): RuntimeException(errorCode.message)