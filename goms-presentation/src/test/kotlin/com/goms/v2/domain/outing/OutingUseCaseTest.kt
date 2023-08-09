package com.goms.v2.domain.outing

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.exception.BlackListNotAllowOutingException
import com.goms.v2.domain.outing.exception.OutingUUIDUnverifiedException
import com.goms.v2.domain.outing.usecase.OutingUseCase
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import com.goms.v2.repository.studentCouncil.OutingUUIDRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import java.util.UUID

class OutingUseCaseTest: BehaviorSpec({
    val outingRepository = mockk<OutingRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val outingUUIDRepository = mockk<OutingUUIDRepository>()
    val accountUtil = mockk<AccountUtil>()
    val outingUseCase = OutingUseCase(outingRepository, outingBlackListRepository, outingUUIDRepository, accountUtil)

    Given("outingUUID가 주어질때") {
        val outingUUID = UUID.randomUUID()
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)

        every { accountUtil.getCurrentAccount() } returns account
        every { outingBlackListRepository.existsById(account.idx) } returns false
        every { outingRepository.existsByAccount(account) } returns false
        every { outingUUIDRepository.existsById(outingUUID) } returns true
        every { outingRepository.save(any()) } returns Unit
        every { outingRepository.deleteByAccountIdx(account.idx) } returns Unit

        When("첫 외출을 요청할때") {
            outingUseCase.execute(outingUUID)

            Then("계정이 outing 테이블에 저장되어야 한다.") {
                verify(exactly = 1) { outingRepository.save(any()) }
            }
        }

        When("외출 요청을 했었던 계정이 요청하면") {
            every { outingRepository.existsByAccount(account) } returns true
            outingUseCase.execute(outingUUID)

            Then("계정이 outing 테이블에서 삭제되어야 한다.") {
                verify(exactly = 1) { outingRepository.deleteByAccountIdx(account.idx) }
            }

            Then("계정이 outing 테이블에 저장되면 안된다.") {
                verify { outingRepository.save(any()) wasNot Called }
            }
        }

        When("외출 블랙리스트에 있는 학생이 외출할 경우") {
            every { outingBlackListRepository.existsById(account.idx) } returns true
            every { outingRepository.existsByAccount(account) } returns false

            Then("BlackListNotAllowOutingException이 터져야 한다.") {
                shouldThrow<BlackListNotAllowOutingException> {
                    outingUseCase.execute(outingUUID)
                }
            }
        }

        When("틀린 외출 식별자로 요청한 경우") {
            every { outingBlackListRepository.existsById(account.idx) } returns false
            every { outingUUIDRepository.existsById(outingUUID) } returns false
            every { outingRepository.existsByAccount(account) } returns false

            Then("OutingUUIDUnverifiedException이 터져야 한다.") {
                shouldThrow<OutingUUIDUnverifiedException> {
                    outingUseCase.execute(outingUUID)
                }
            }
        }
    }
})