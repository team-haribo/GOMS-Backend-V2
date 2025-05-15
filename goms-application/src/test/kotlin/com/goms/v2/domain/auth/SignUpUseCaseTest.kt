package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AuthenticationValidator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.data.event.DeleteAuthenticationEvent
import com.goms.v2.domain.auth.exception.AlreadyExistEmailException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.domain.auth.usecase.SignUpUseCase
import com.goms.v2.repository.account.AccountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class SignUpUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val passwordEncoderPort = mockk<PasswordEncoderPort>()
    val authenticationValidator = mockk<AuthenticationValidator>()
    val publisher = mockk<ApplicationEventPublisher>()
    val signUpUseCase = SignUpUseCase(accountRepository, passwordEncoderPort, authenticationValidator, publisher)

    Given("SignUpDto 가 주어질때") {
        val encodePassword = "encodePassword"
        val email = "s23039@gsm.hs.kr"
        val authentication = AnyValueObjectGenerator.anyValueObject<Authentication>("email" to email)
        val account = AnyValueObjectGenerator.anyValueObject<Account>("email" to email)
        val signUpDto = AnyValueObjectGenerator.anyValueObject<SignUpDto>("email" to email)
        val deleteAuthenticationEvent = AnyValueObjectGenerator.anyValueObject<DeleteAuthenticationEvent>("authentication" to authentication)

        every { accountRepository.existsByEmail(signUpDto.email) } returns false
        every { authenticationValidator.verifyAuthenticationByEmail(signUpDto.email) } returns authentication
        every { publisher.publishEvent(deleteAuthenticationEvent) } returns Unit
        every { passwordEncoderPort.passwordEncode(signUpDto.password) } returns encodePassword
        every { accountRepository.save(any()) } returns account

        When("회원가입 요청을 하면") {
            signUpUseCase.execute(signUpDto)

            Then("Account 가 저장이 되어야 한다.") {
                verify(exactly = 1) { accountRepository.save(any()) }
            }
        }

        When("이미 존재하는 email로 요청을 하면") {
            every { accountRepository.existsByEmail(signUpDto.email) } returns true

            Then("AlreadyExistEmailException이 터져야 한다.") {
                shouldThrow<AlreadyExistEmailException> {
                    signUpUseCase.execute(signUpDto)
                }
            }
        }
    }
})
