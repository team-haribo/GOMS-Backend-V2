package com.goms.v2.global.config

import com.goms.v2.common.property.studentCouncil.OutingUUIDExpTimeProperties
import com.goms.v2.global.security.jwt.common.properties.JwtExpTimeProperties
import com.goms.v2.global.security.jwt.common.properties.JwtProperties
import com.goms.v2.thirdparty.gauth.property.GAuthProperties
import com.goms.v2.thirdparty.notification.property.FcmProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(
    basePackageClasses = [
        JwtProperties::class,
        JwtExpTimeProperties::class,
        GAuthProperties::class,
        OutingUUIDExpTimeProperties::class,
        FcmProperties::class
    ]
)
class PropertiesScanConfig