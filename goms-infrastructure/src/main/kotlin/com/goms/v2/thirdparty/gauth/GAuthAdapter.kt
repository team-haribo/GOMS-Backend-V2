package com.goms.v2.thirdparty.gauth

import com.goms.v2.domain.auth.dto.GAuthTokenDto
import com.goms.v2.domain.auth.dto.GAuthUserInfoDto
import com.goms.v2.spi.GAuthPort
import com.goms.v2.global.gauth.property.GAuthProperties
import com.goms.v2.persistence.auth.mapper.GAuthDataMapper
import gauth.GAuth
import org.springframework.stereotype.Component

@Component
class GAuthAdapter(
    private val gAuth: GAuth,
    private val gAuthDataMapper: GAuthDataMapper,
    private val gAuthProperties: GAuthProperties
): GAuthPort {
    override fun receiveGAuthToken(code: String): GAuthTokenDto =
        gAuthDataMapper.toDto(gAuth.generateToken(
            code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        ))

    override fun receiveUserInfo(accessToken: String): GAuthUserInfoDto =
    gAuthDataMapper.toDto(gAuth.getUserInfo(accessToken))

}