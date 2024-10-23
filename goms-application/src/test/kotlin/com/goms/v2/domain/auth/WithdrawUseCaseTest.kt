package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.domain.auth.usecase.WithdrawUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.late.LateRepository
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

class WithdrawUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val lateRepository = mockk<LateRepository>()
    val outingRepository = mockk<OutingRepository>()
    val accountUtil = mockk<AccountUtil>()
    val passwordEncoderPort = mockk<PasswordEncoderPort>()
    val withdrawUseCase = WithdrawUseCase(accountRepository, lateRepository, outingRepository, accountUtil, passwordEncoderPort)

    Given("password가 주어지면") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val password = ""

        every { accountUtil.getCurrentAccountIdx() } returns accountIdx
        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { passwordEncoderPort.isPasswordMatch(password, account.password) } returns true
        every { lateRepository.deleteAllByAccountIdx(accountIdx) } returns Unit
        every { outingRepository.deleteAllByAccountIdx(accountIdx) } returns Unit
        every { accountRepository.delete(account) } returns Unit

        When("회원탈퇴 요청을 하면") {
            withdrawUseCase.execute(password)

            Then("계정이 삭제되어야 한다.") {
                verify(exactly = 1) { lateRepository.deleteAllByAccountIdx(accountIdx) }
                verify(exactly = 1) { outingRepository.deleteAllByAccountIdx(accountIdx) }
                verify(exactly = 1) { accountRepository.delete(account) }
            }
        }

        When("존재하지 않는 accountIdx 라면") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    withdrawUseCase.execute(password)
                }
            }
        }

        When("password가 일치하지 않는다면") {
            every { passwordEncoderPort.isPasswordMatch(password, account.password) } returns false

            Then("PasswordNotMachException이 터져야 한다.") {
                shouldThrow<PasswordNotMatchException> {
                    withdrawUseCase.execute(password)
                }
            }
        }
    }
})
