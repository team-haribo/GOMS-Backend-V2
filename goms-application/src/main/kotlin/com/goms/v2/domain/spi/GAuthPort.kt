package com.goms.v2.domain.spi

import com.goms.v2.domain.auth.dto.GAuthTokenDto
import com.goms.v2.domain.auth.dto.GAuthUserInfoDto

interface GAuthPort {

    fun receiveGAuthToken(code: String): GAuthTokenDto
    fun receiveUserInfo(accessToken: String): GAuthUserInfoDto

}