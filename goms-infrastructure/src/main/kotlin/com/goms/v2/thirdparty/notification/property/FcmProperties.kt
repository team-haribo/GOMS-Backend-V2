package com.goms.v2.thirdparty.notification.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("fcm")
class FcmProperties(
    val credential: String
)
