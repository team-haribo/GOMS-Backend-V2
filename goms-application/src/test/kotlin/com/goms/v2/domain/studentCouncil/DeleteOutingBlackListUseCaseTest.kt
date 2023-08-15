package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.studentCouncil.usecase.DeleteOutingBlacklistUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

class DeleteOutingBlackListUseCaseTest: BehaviorSpec({
    val accountRepository = mockk<AccountRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val deleteOutingBlackList = DeleteOutingBlacklistUseCase(accountRepository, outingBlackListRepository)

    Given("accountIdx가 주어질때") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)

        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { outingBlackListRepository.deleteById(account.idx) } returns Unit

        When("외출 블랙리스트 삭제 요청하면") {
            deleteOutingBlackList.execute(accountIdx)

            Then("외출 블랙리스트 삭제가 되어야 한다.") {
                verify(exactly = 1) { outingBlackListRepository.deleteById(account.idx) }
            }
        }

        When("없는 계정 idx일 경우") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFound가 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    deleteOutingBlackList.execute(accountIdx)
                }
            }
        }
    }
})