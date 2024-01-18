package com.goms.v2.domain.auth.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class AlreadyExistPhoneNumberException : GomsException(ErrorCode.ALREADY_EXIST_PHONE_NUMBER)