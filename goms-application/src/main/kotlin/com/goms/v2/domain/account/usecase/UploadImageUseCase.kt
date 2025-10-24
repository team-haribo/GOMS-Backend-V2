package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.account.updateProfileUrl
import com.goms.v2.repository.account.AccountRepository
import org.springframework.web.multipart.MultipartFile

@UseCaseWithTransaction
class UploadImageUseCase(
    private val accountRepository: AccountRepository,
    private val s3UtilPort: S3UtilPort,
) {

    fun execute(image: MultipartFile) {
        val account = s3UtilPort.validImage(image)

        val imgURL = s3UtilPort.upload(image)
        account.updateProfileUrl(imgURL)
        accountRepository.save(account)
    }

}