package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.exception.ProfileUrlNotExistException
import com.goms.v2.domain.account.resetProfileUrl
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository

@UseCaseWithTransaction
class DeleteImageUseCase(
    private val accountRepository: AccountRepository,
    private val s3UtilPort: S3UtilPort,
    private val accountUtil: AccountUtil
) {

    fun execute() {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()

        if (account.profileUrl == null) throw ProfileUrlNotExistException()

        s3UtilPort.deleteImage(account.profileUrl.toString())
        account.resetProfileUrl(null)

        accountRepository.save(account)
    }

}