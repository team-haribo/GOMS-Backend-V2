package com.goms.v2.spi

import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.dto.response.TokenInDto
import java.util.*

interface TokenPort {

    fun generateToken(accountIdx: UUID, authority: Authority): TokenInDto

}