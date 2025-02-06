package com.goms.v2.domain.studentCouncil.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class OutingAlreadyExistException : GomsException(ErrorCode.OUTING_ALREADY_EXIST)