package com.goms.v2.global.log

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.goms.v2.thirdparty.aws.s3.AwsS3Properties
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File

@Component
class LoggingScheduler(
    private val amazonS3: AmazonS3,
    private val awsS3Properties: AwsS3Properties
) {

    @Scheduled(cron = "0 3 * * * ?")
    fun sendLog() {
        val logDir = "./logs/"
        val logDirectory = File(logDir)
        logDirectory.listFiles()
            .forEach { file ->
                val fileName = file.name
                val objectMetadata = ObjectMetadata()
                objectMetadata.contentLength = file.length()
                objectMetadata.contentType = "text/plain"
                amazonS3.putObject(
                    PutObjectRequest(awsS3Properties.bucketLog, fileName, file.inputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
                file.delete()
            }
    }

}