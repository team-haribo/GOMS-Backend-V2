package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.usecase.QueryAllAccountUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class QueryAllAccountUseCaseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val outingRepository = mockk<OutingRepository>()
    val queryAllAccountUseCase = QueryAllAccountUseCase(accountRepository, outingRepository, outingBlackListRepository)

    Given("계정이 주어질 때") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val outingBlackListIdx = listOf(accountIdx)
        val outingAccounts = listOf(accountIdx)

        val expectedAccountDto = AccountDto(
            accountIdx = accountIdx,
            name = account.name,
            grade = account.grade,
            gender = account.gender,
            major = account.major,
            profileUrl = account.profileUrl ?: "",
            authority = account.authority,
            isBlackList = outingBlackListIdx.contains(accountIdx),
            outing = outingAccounts.contains(accountIdx)
        )

        every { accountRepository.findAllOrderByStudentNum() } returns listOf(account)
        every { outingBlackListRepository.findAll() } returns outingBlackListIdx.map { AnyValueObjectGenerator.anyValueObject("accountIdx" to it) }
        every { outingRepository.findAllOutingAccountIdx() } returns outingAccounts

        When("모든 계정 조회 요청을 하면") {
            val result = queryAllAccountUseCase.execute()

            Then("result와 예상된 AccountDto 리스트는 같아야 한다.") {
                result shouldBe listOf(expectedAccountDto)
            }
        }
    }
})