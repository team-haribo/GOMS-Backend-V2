package com.goms.v2.common.spi

import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.dto.response.TokenInDto
import java.util.*

interface JwtPort {

    fun generateToken(accountIdx: UUID, authority: Authority): TokenInDto

}