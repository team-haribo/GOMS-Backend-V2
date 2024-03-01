package com.goms.v2.thirdparty.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.exception.FileExtensionInvalidException
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class S3UtilAdapter(
        private val amazonS3: AmazonS3,
        private val accountUtil: AccountUtil,
        private val accountRepository: AccountRepository
): S3UtilPort {

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null

    override fun validImage(image: MultipartFile): Account {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()

        val list = listOf("jpg", "jpeg", "png", "gif")
        val splitFile = image.originalFilename.toString().split(".")

        if(splitFile.size < 2)
            throw FileExtensionInvalidException()

        val extension = splitFile[1].lowercase()

        if(list.none { it == extension })
            throw FileExtensionInvalidException()

        return account;
    }

    override fun upload(image: MultipartFile): String {
        val profileName = "${bucket}/${UUID.randomUUID()}${image.originalFilename}"
        val metadata = ObjectMetadata()
        metadata.contentLength = file.inputStream.available().toLong()
        amazonS3.putObject(bucket, profileName, file.inputStream, metadata)
        return amazonS3.getUrl(bucket, profileName).toString()
    }

}