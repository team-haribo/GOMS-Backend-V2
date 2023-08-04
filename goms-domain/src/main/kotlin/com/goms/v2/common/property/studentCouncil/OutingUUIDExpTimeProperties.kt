package com.goms.v2.common.property.studentCouncil

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("outing")
class OutingUUIDExpTimeProperties(
	val expiredAt: Int
)