package com.goms.v2.global.security.jwt.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("jwt.time")
class JwtExpTimeProperties(
    val accessExp: Int,
    val refreshExp: Int
)