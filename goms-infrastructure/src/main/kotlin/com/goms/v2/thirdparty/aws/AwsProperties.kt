package com.goms.v2.thirdparty.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("cloud.aws.credentials")
class AwsProperties(
    val accessKey: String,
    val secretKey: String
)