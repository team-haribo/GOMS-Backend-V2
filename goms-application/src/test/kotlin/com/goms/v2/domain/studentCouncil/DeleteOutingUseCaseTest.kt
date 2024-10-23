package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.studentCouncil.usecase.DeleteOutingUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class DeleteOutingUseCaseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val outingRepository = mockk<OutingRepository>()
    val deleteOutingUseCase = DeleteOutingUseCase(accountRepository, outingRepository)

    Given("accountIdx가 주어졌을때") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)

        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { outingRepository.deleteByAccountIdx(accountIdx) } returns Unit

        When("외출자 리스트 삭제를 요청하면") {
            deleteOutingUseCase.execute(accountIdx)

            Then("외출자 리스트에서 삭제되야 한다.") {
                verify(exactly = 1) { outingRepository.deleteByAccountIdx(accountIdx) }
            }
        }

        When("없는 계정 index일 경우") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFound가 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    deleteOutingUseCase.execute(accountIdx)
                }
            }
        }

    }
})
