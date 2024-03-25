package com.goms.v2.domain.account.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class PasswordNotMatchException: GomsException(ErrorCode.PASSWORD_NOT_MATCH)