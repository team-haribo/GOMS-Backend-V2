package com.goms.v2.thirdparty.aws.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("cloud.aws.s3")
class AwsS3Properties(
    val bucket: String,
    val bucketLog: String
)