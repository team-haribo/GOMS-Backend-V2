package com.goms.v2.domain.account

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.account.usecase.DeleteImageUseCase
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import java.util.UUID

class DeleteImageUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val s3UtilPort = mockk<S3UtilPort>()
    val accountUtil = mockk<AccountUtil>()
    val deleteImageUseCase = DeleteImageUseCase(accountRepository, s3UtilPort, accountUtil)


    Given("프로필 이미지가 존재할 때") {
        val accountIdx = UUID.randomUUID()
        val account = spyk<Account>(AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx))
        val profileURL = account.profileUrl

        every { accountUtil.getCurrentAccountIdx() } returns accountIdx
        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { s3UtilPort.deleteImage(account.profileUrl.toString()) } returns Unit
        every { accountRepository.save(account) } returns account

        When("프로필 이미지 삭제 요청이 들어오면") {
            deleteImageUseCase.execute()

            Then("프로필 이미지를 지운 후에 Account가 저장되어야한다.") {
                verify(exactly = 1) { s3UtilPort.deleteImage((profileURL.toString())) }
                verify(exactly = 1) { account.resetProfileUrl(null) }
                verify(exactly = 1) { accountRepository.save(account) }
            }
        }

        When("Account가 존재하지 않는다면") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    deleteImageUseCase.execute()
                }
            }
        }

    }
})
