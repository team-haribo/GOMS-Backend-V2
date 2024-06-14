package com.goms.v2.domain.account.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class FileExtensionInvalidException: GomsException(ErrorCode.FILE_EXTENSION_INVALID)