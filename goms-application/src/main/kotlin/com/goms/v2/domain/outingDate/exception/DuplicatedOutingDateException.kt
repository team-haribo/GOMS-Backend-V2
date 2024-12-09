package com.goms.v2.domain.outingDate.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class DuplicatedOutingDateException: GomsException(ErrorCode.DUPLICATED_OUTING_DATE)