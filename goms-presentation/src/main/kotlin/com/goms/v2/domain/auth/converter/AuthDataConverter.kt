package com.goms.v2.domain.auth.converter

import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse

interface AuthDataConverter {

    fun toResponse(tokeInDto: TokenDto): TokenHttpResponse

}