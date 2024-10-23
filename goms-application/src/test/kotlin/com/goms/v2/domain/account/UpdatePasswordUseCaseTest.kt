package com.goms.v2.domain.account

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AuthenticationValidator
import com.goms.v2.domain.account.data.dto.PasswordDto
import com.goms.v2.domain.account.exception.DuplicatedNewPasswordException
import com.goms.v2.domain.account.usecase.UpdatePasswordUseCase
import com.goms.v2.domain.auth.Authentication
import com.goms.v2.domain.auth.data.event.DeleteAuthenticationEvent
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class UpdatePasswordUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val authenticationValidator = mockk<AuthenticationValidator>()
    val passwordEncoderPort = mockk<PasswordEncoderPort>()
    val publisher = mockk<ApplicationEventPublisher>()
    val updatePasswordUseCase = UpdatePasswordUseCase(accountRepository, authenticationValidator, passwordEncoderPort, publisher)

    Given("passwordDto가 주어질 때") {
        val email = "s22039@gsm.hs.kr"
        val encodePassword = "encodePassword"
        val passwordDto = AnyValueObjectGenerator.anyValueObject<PasswordDto>("email" to email)
        val account = AnyValueObjectGenerator.anyValueObject<Account>("email" to email)
        val authentication = AnyValueObjectGenerator.anyValueObject<Authentication>("email" to email)

        every { accountRepository.findByEmail(passwordDto.email) } returns account
        every { passwordEncoderPort.isPasswordMatch(passwordDto.newPassword, account.password) } returns false
        every { authenticationValidator.verifyAuthenticationByEmail(passwordDto.email) } returns authentication
        every { publisher.publishEvent(DeleteAuthenticationEvent(authentication)) } returns Unit
        every { passwordEncoderPort.passwordEncode(passwordDto.newPassword) } returns encodePassword
        every { accountRepository.save(account) } returns account

        When("비밀번호 변경 요청을 하면") {
            updatePasswordUseCase.execute(passwordDto)

            Then("Authentication 삭제 이벤트가 발생해야한다.") {
                verify(exactly = 1) { publisher.publishEvent(DeleteAuthenticationEvent(authentication)) }
            }
            Then("Account 가 저장이 되어야 한다.") {
                verify(exactly = 1) { accountRepository.save(any()) }
            }
        }
        When("없는 계정으로 요청을 하면") {
            every { accountRepository.findByEmail(passwordDto.email) } returns null

            Then("AccountNotFoundExceptino이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    updatePasswordUseCase.execute(passwordDto)
                }
            }
        }
        When("이미 사용중인 비밀번호로 변경하면") {
            every { passwordEncoderPort.isPasswordMatch(passwordDto.newPassword, account.password) } returns true

            Then("DuplicatedNewPasswordException 이 터져야 한다.") {
                shouldThrow<DuplicatedNewPasswordException> {
                    updatePasswordUseCase.execute(passwordDto)
                }
            }
        }
    }
})
