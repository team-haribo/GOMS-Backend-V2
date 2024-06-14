package com.goms.v2.domain.late.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class LateAccountNotFound: GomsException(ErrorCode.LATE_ACCOUNT_NOT_FOUND)