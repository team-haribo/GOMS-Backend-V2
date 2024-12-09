package com.goms.v2.domain.outingDate.exception

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException

class UnauthorizedDiscordClientTokenException: GomsException(ErrorCode.UNAUTHORIZED_DISCORD_CLIENT_TOKEN)