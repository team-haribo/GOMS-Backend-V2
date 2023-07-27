package com.goms.v2.global.error.exception

import com.goms.v2.global.error.ErrorCode

open class GomsException(val errorCode: ErrorCode): RuntimeException(errorCode.message)