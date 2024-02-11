package com.goms.v2.thirdparty.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.goms.v2.domain.account.spi.S3UtillPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.multipart.MultipartFile
import java.util.*

class S3Util(
        private val amazonS3: AmazonS3
): S3UtillPort {

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null
    override fun upload(file: MultipartFile): String {
        val profileName = "${bucket}/${UUID.randomUUID()}${file.originalFilename}"
        val metadata = ObjectMetadata()
        metadata.contentLength = file.inputStream.available().toLong()
        amazonS3.putObject(bucket, profileName, file.inputStream, metadata)
        return amazonS3.getUrl(bucket, profileName).toString()
    }

}