package com.goms.v2.domain.account

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.data.dto.ChangePasswordDto
import com.goms.v2.domain.account.exception.DuplicatedNewPasswordException
import com.goms.v2.domain.account.usecase.ChangePasswordUseCase
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import java.util.*

class ChangePasswordUseCaseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val passwordEncoderPort = mockk<PasswordEncoderPort>()
    val accountUtil = mockk<AccountUtil>()
    val changePasswordUseCase = ChangePasswordUseCase(accountRepository, passwordEncoderPort, accountUtil)

    Given("ChangePasswordDto가 주어지면") {
        val accountIdx = UUID.randomUUID()
        val newPassword = "123"
        val encodedPassword = "encodedPassword"
        val account = spyk<Account>(AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx))
        val changePasswordDto = AnyValueObjectGenerator.anyValueObject<ChangePasswordDto>("newPassword" to newPassword)

        every { accountUtil.getCurrentAccountIdx() } returns accountIdx
        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { passwordEncoderPort.isPasswordMatch(changePasswordDto.password, account.password) } returns true
        every { passwordEncoderPort.isPasswordMatch(changePasswordDto.newPassword, account.password) } returns false
        every { passwordEncoderPort.passwordEncode(changePasswordDto.newPassword) } returns encodedPassword
        every { accountRepository.save(account) } returns account

        When("비밀번호 변경 요청을 하면") {
            changePasswordUseCase.execute(changePasswordDto)

            Then("비밀번호가 변경된 상태로 Account가 저장되어야 한다.") {
                verify(exactly = 1) { account.updatePassword(encodedPassword) }
                verify(exactly = 1) { accountRepository.save(account) }
            }
        }

        When("Account가 존재하지 않는다면") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFoundException을 터쳐야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    changePasswordUseCase.execute(changePasswordDto)
                }
            }
        }

        When("기존 비밀번호가 일치하지 않으면") {
            every { passwordEncoderPort.isPasswordMatch(changePasswordDto.password, account.password) } returns false

            Then("PasswordNotMatchException이 터져야 한다.") {
                shouldThrow<PasswordNotMatchException> {
                    changePasswordUseCase.execute(changePasswordDto)
                }
            }
        }

        When("새로운 비밀번호와 기존 비밀번호가 동일하면") {
            every { passwordEncoderPort.isPasswordMatch(changePasswordDto.newPassword, account.password) } returns true

            Then("DuplicatedNewPasswordException이 터져야 한다.") {
                shouldThrow<DuplicatedNewPasswordException> {
                    changePasswordUseCase.execute(changePasswordDto)
                }
            }
        }
    }
})
