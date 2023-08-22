package com.goms.v2.domain.email

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.email.data.dto.EmailDto
import com.goms.v2.domain.email.spi.EmailSendPort
import com.goms.v2.domain.email.usecase.SendEmailUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.email.AuthCodeRepository
import com.goms.v2.repository.email.AuthenticationRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class SendEmailUseCaseTest: BehaviorSpec({
    val emailSendPort = mockk<EmailSendPort>()
    val accountRepository = mockk<AccountRepository>()
    val authCodeRepository = mockk<AuthCodeRepository>()
    val authenticationRepository = mockk<AuthenticationRepository>()
    val sendEmailUseCase = SendEmailUseCase(
        emailSendPort,
        accountRepository,
        authCodeRepository,
        authenticationRepository
    )

    Given("EmailDto이 주어졌을때") {
        val email = "test@test.com"
        val emailDto = AnyValueObjectGenerator.anyValueObject<EmailDto>("email" to email)
        val authentication = AnyValueObjectGenerator.anyValueObject<Authentication>("email" to email)
        val authCode = AnyValueObjectGenerator.anyValueObject<AuthCode>("email" to email)

        every { authenticationRepository.existByEmail(emailDto.email) } returns true
        every { authenticationRepository.findByIdOrNull(emailDto.email) } returns authentication
        every { authenticationRepository.save(any()) } returns Unit
        every { accountRepository.existsByEmail(emailDto.email) } returns true
        every { authCodeRepository.findByIdOrNull(emailDto.email) } returns authCode
        every { authCodeRepository.save(any()) } returns Unit
        every { emailSendPort.sendEmail(any(), any()) } returns Unit

        When("권한이 있는 사용자 이면") {
            val isExistsAuthentication = authenticationRepository.existByEmail(emailDto.email)
            sendEmailUseCase.execute(emailDto)

            Then("isExistsAuthentication이 true 여야 한다.") {
                isExistsAuthentication shouldBe true
            }

            Then("email이 발송되어야 한다.") {
                verify(exactly = 1) { emailSendPort.sendEmail(any(), any()) }
            }

            Then("권한이 저장되어야 한다.") {
                verify(exactly = 1) { authenticationRepository.save(authentication) }
            }

            Then("인증코드가 저장되어야 한다.") {
                verify(exactly = 1) { authCodeRepository.save(authCode) }
            }
        }

        When("권한이 없는 사용자 이면") {
            every { authenticationRepository.existByEmail(emailDto.email) } returns false
            val isExistsAuthentication = authenticationRepository.existByEmail(emailDto.email)
            sendEmailUseCase.execute(emailDto)

            Then("email이 발송되어야 한다.") {
                verify(exactly = 2) { emailSendPort.sendEmail(any(), any()) }
            }

            Then("isExistsAuthentication이 false여야 한다.") {
                isExistsAuthentication shouldBe false
            }

            Then("권한이 저장되어야 한다.") {
                verify(exactly = 1) { authenticationRepository.save(authentication) }
            }

            Then("인증코드가 저장되어야 한다.") {
                verify(exactly = 2) { authCodeRepository.save(authCode) }
            }
        }

    }

})