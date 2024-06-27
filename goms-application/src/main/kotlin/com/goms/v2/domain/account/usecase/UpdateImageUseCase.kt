package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.account.updateProfileUrl
import com.goms.v2.repository.account.AccountRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.web.multipart.MultipartFile

@UseCaseWithTransaction
class UpdateImageUseCase(
    private val accountRepository: AccountRepository,
    private val s3UtilPort: S3UtilPort,
    private val accountUtil: AccountUtil
) {

    @CacheEvict(
        value = ["userProfiles"],
        key = "#root.target.generateCacheKey()",
        cacheManager = "contentCacheManager"
    )
    fun execute(image: MultipartFile) {
        val account = s3UtilPort.validImage(image)
        s3UtilPort.deleteImage(account.profileUrl.toString())

        val imgURL = s3UtilPort.upload(image)
        account.updateProfileUrl(imgURL)
        accountRepository.save(account)

    }

    fun generateCacheKey(): String =
        accountUtil.getCurrentAccountIdx().toString()


}