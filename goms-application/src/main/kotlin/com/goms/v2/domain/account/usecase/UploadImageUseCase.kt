package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.exception.FileExtensionInvalidException
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.account.updateProfileUrl
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import org.springframework.web.multipart.MultipartFile

@UseCaseWithTransaction
class UploadImageUseCase(
    private val accountRepository: AccountRepository,
    private val s3UtilPort: S3UtilPort,
    private val accountUtil: AccountUtil
) {

    fun execute(image: MultipartFile) {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()

        val list = listOf("jpg", "jpeg", "png", "gif")
        val splitFile = image.originalFilename.toString().split(".")

        if(splitFile.size < 2)
            throw FileExtensionInvalidException()

        val extension = splitFile[1].lowercase()

        if(list.none { it == extension })
            throw FileExtensionInvalidException()

        val imgURL = s3UtilPort.upload(image)
        account.updateProfileUrl(imgURL)
        accountRepository.save(account)
    }

}