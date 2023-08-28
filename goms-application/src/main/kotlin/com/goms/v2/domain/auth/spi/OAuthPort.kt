package com.goms.v2.domain.auth.spi

import com.goms.v2.domain.auth.data.dto.OAuthTokenDto
import com.goms.v2.domain.auth.data.dto.OAuthUserInfoDto

interface OAuthPort {

    fun receiveOAuthToken(code: String): OAuthTokenDto
    fun receiveUserInfo(accessToken: String): OAuthUserInfoDto

}