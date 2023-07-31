package com.goms.v2.gloabl.exception.exception

import com.goms.v2.gloabl.exception.ErrorCode

open class GomsException(val errorCode: ErrorCode): RuntimeException(errorCode.message)