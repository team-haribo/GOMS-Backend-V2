package com.goms.v2.thirdparty.gauth

import com.goms.v2.domain.auth.dto.GAuthTokenDto
import com.goms.v2.domain.auth.dto.GAuthUserInfoDto
import com.goms.v2.domain.auth.exception.*
import com.goms.v2.domain.auth.spi.GAuthPort
import com.goms.v2.thirdparty.gauth.property.GAuthProperties
import com.goms.v2.persistence.auth.mapper.GAuthDataMapper
import gauth.GAuth
import gauth.exception.GAuthException
import org.springframework.stereotype.Component

@Component
class GAuthAdapter(
    private val gAuth: GAuth,
    private val gAuthDataMapper: GAuthDataMapper,
    private val gAuthProperties: GAuthProperties,
): GAuthPort {

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

    private fun gAuthExceptionHandler(error: GAuthException): Exception {
        return when (error.code) {
            400 -> SecretMismatchException()
            401 -> ExpiredCodeException()
            404 -> ServiceNotFoundException()
            else -> InternalServerErrorException()
        }
    }

}