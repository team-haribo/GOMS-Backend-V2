package com.goms.v2.thirdparty.gauth

import com.goms.v2.domain.auth.dto.GAuthExceptionDto
import com.goms.v2.domain.auth.dto.GAuthTokenDto
import com.goms.v2.domain.auth.dto.GAuthUserInfoDto
import com.goms.v2.domain.auth.spi.GAuthPort
import com.goms.v2.global.gauth.property.GAuthProperties
import com.goms.v2.persistence.auth.mapper.GAuthDataMapper
import gauth.GAuth
import gauth.exception.GAuthException
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

    override fun receiveGAuthException(error: Exception): GAuthExceptionDto =
        gAuthDataMapper.toDto(GAuthException(error.hashCode()))

}