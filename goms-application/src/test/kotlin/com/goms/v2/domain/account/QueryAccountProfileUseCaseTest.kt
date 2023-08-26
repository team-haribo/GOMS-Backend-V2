package com.goms.v2.domain.account

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.usecase.QueryAccountProfileUseCase
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.late.LateRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class QueryAccountProfileUseCaseTest: BehaviorSpec({
    val accountUtil = mockk<AccountUtil>()
    val lateRepository = mockk<LateRepository>()
    val outingRepository = mockk<OutingRepository>()
    val accountRepository = mockk<AccountRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val queryAccountProfileUseCase = QueryAccountProfileUseCase(accountUtil, lateRepository, outingRepository, accountRepository, outingBlackListRepository)

    Given("계정이 주어질때") {
        val email = "test@test.com"
        val account = AnyValueObjectGenerator.anyValueObject<Account>("email" to email)
        val profileDto = AnyValueObjectGenerator.anyValueObject<ProfileDto>("name" to account.name)

        every { accountUtil.getCurrentAccountIdx() } returns account.idx
        every { lateRepository.countByAccountIdx(account.idx) } returns 0
        every { accountRepository.findByIdOrNull(account.idx) } returns account
        every { outingRepository.existsByAccount(account) } returns false
        every { outingBlackListRepository.existsById(account.idx) } returns false

        When("프로필 요청을 하면") {
            val result = queryAccountProfileUseCase.execute()

            Then("result와 profileDto는 같아야 한다.") {
                result shouldBe profileDto
            }
        }

        When("없는 계정으로 요청을 하면") {
            every { accountRepository.findByIdOrNull(account.idx) } returns null

            Then("AccountNotFoundExceptino이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    queryAccountProfileUseCase.execute()
                }
            }
        }
    }
})