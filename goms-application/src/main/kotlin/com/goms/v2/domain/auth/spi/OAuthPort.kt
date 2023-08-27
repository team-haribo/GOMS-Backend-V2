package com.goms.v2.domain.auth.spi

import com.goms.v2.domain.auth.data.dto.GAuthTokenDto
import com.goms.v2.domain.auth.data.dto.GAuthUserInfoDto

interface OAuthPort {

    fun receiveGAuthToken(code: String): GAuthTokenDto
    fun receiveUserInfo(accessToken: String): GAuthUserInfoDto

}