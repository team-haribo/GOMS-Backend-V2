package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.studentCouncil.usecase.SaveOutingBlackListUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

class SaveOutingBlackListUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val saveOutingBlackListUseCase = SaveOutingBlackListUseCase(accountRepository, outingBlackListRepository)

    Given("accountIdx가 주어질때") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)

        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { outingBlackListRepository.save(any()) } returns Unit

        When("외출 블랙리스트 저장을 요청하면") {
            saveOutingBlackListUseCase.execute(accountIdx)

            Then("외출 블랙리스트 테이블에 저장되어야 한다.") {
                verify(exactly = 1) { outingBlackListRepository.save(any()) }
            }
        }

        When("없는 계정 index일 경우") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFound가 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    saveOutingBlackListUseCase.execute(accountIdx)
                }
            }
        }
    }
})
