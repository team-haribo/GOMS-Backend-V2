package com.goms.v2.domain.auth.exception

import com.goms.v2.gloabl.exception.ErrorCode
import com.goms.v2.gloabl.exception.exception.GomsException

class AccountNotFoundException: GomsException(ErrorCode.ACCOUNT_NOT_FOUND)