package com.goms.v2.domain.auth.spi

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.auth.data.dto.TokenDto
import java.util.*

interface TokenPort {

    fun generateToken(accountIdx: UUID, authority: Authority): TokenDto

}