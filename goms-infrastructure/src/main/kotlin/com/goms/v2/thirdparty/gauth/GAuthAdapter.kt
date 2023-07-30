package com.goms.v2.thirdparty.gauth

import com.goms.v2.common.spi.GAuthPort
import com.goms.v2.global.gauth.property.GAuthProperties
import gauth.GAuth
import gauth.GAuthToken
import org.springframework.stereotype.Component

@Component
class GAuthAdapter(
    private val gAuth: GAuth,
    private val gAuthProperties: GAuthProperties
): GAuthPort {
    override fun receiveGAuthToken(code: String): GAuthToken =
        gAuth.generateToken(
            code,
            gAuthProperties.clientId,
            gAuthProperties.clientSecret,
            gAuthProperties.redirectUri
        )

}