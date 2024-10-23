package com.goms.v2.domain.account

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.spi.S3UtilPort
import com.goms.v2.domain.account.usecase.UpdateImageUseCase
import com.goms.v2.repository.account.AccountRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.springframework.mock.web.MockMultipartFile
import java.nio.charset.StandardCharsets
import java.util.*

class UpdateImageUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val s3UtilPort = mockk<S3UtilPort>()
    val updateImageUseCase = UpdateImageUseCase(accountRepository, s3UtilPort)

    Given("multipart image가 주어질 때") {
        val imageBytes = "image content".toByteArray(StandardCharsets.UTF_8)
        val image = MockMultipartFile("File", "image.png", "image/png", imageBytes)

        val imageURL = ""
        val accountIdx = UUID.randomUUID()
        val account = spyk<Account>(AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx))

        every { s3UtilPort.validImage(image) } returns account
        every { s3UtilPort.deleteImage(account.profileUrl.toString()) } returns Unit
        every { s3UtilPort.upload(image) } returns imageURL
        every { accountRepository.save(account) } returns account

        When("프로필 이미지 수정 요청을 하면") {
            updateImageUseCase.execute(image)

            Then("account가 저장되어야 한다.") {
                verify(exactly = 1) { account.updateProfileUrl(imageURL) }
                verify(exactly = 1) { accountRepository.save(account) }
            }
        }
    }
})
