package com.goms.v2.domain.email.exception

import com.goms.v2.gloabl.exception.ErrorCode
import com.goms.v2.gloabl.exception.exception.GomsException

class EmailSendFailException: GomsException(ErrorCode.MAIL_SEND_FAIL)