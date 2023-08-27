package com.goms.v2.thirdparty.gauth

import com.goms.v2.domain.auth.data.dto.GAuthTokenDto
import com.goms.v2.domain.auth.data.dto.GAuthUserInfoDto
import com.goms.v2.domain.auth.exception.*
import com.goms.v2.domain.auth.exception.ExpiredGAuthCodeException
import com.goms.v2.domain.auth.exception.GAuthSecretMismatchException
import com.goms.v2.domain.auth.exception.GAuthServiceNotFoundException
import com.goms.v2.domain.auth.spi.OAuthPort
import com.goms.v2.common.exception.GomsException
import com.goms.v2.thirdparty.gauth.property.GAuthProperties
import com.goms.v2.thirdparty.gauth.mapper.GAuthDataMapper
import gauth.GAuth
import gauth.exception.GAuthException
import org.springframework.stereotype.Component

@Component
class GAuthAdapter(
    private val gAuth: GAuth,
    private val gAuthDataMapper: GAuthDataMapper,
    private val gAuthProperties: GAuthProperties
): OAuthPort {

    override fun receiveGAuthToken(code: String): GAuthTokenDto {
        return try {
            gAuthDataMapper.toDto(
                gAuth.generateToken(
                    code,
                    gAuthProperties.clientId,
                    gAuthProperties.clientSecret,
                    gAuthProperties.redirectUri
                )
            )
        } catch (error: GAuthException) {
            throw gAuthExceptionHandler(error)
        }
    }

    override fun receiveUserInfo(accessToken: String): GAuthUserInfoDto {
        return try {
            gAuthDataMapper.toDto(gAuth.getUserInfo(accessToken))
        } catch (error: GAuthException) {
            throw gAuthExceptionHandler(error)
        }
    }

    private fun gAuthExceptionHandler(error: GAuthException): GomsException {
        return when (error.code) {
            400 -> GAuthSecretMismatchException()
            401 -> ExpiredGAuthCodeException()
            404 -> GAuthServiceNotFoundException()
            else -> InternalServerErrorException()
        }
    }

}