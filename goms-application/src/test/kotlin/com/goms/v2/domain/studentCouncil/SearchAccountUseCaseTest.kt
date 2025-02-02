package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.SearchAccountDto
import com.goms.v2.domain.studentCouncil.usecase.SearchAccountUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.UUID

class SearchAccountUseCaseTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val accountRepository = mockk<AccountRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val outingRepository = mockk<OutingRepository>()
    val searchAccountUseCase = SearchAccountUseCase(accountRepository, outingRepository, outingBlackListRepository)

    Given("계정 검색 키워드가 주어질 때") {
        val searchAccountDto = SearchAccountDto(
            grade = 0,
            gender = Gender.MAN,
            name = "",
            authority = Authority.ROLE_STUDENT,
            isBlackList = true,
            major = Major.SMART_IOT
        )

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

        every { accountRepository.findAccountByStudentInfo(searchAccountDto.grade, searchAccountDto.gender, searchAccountDto.name, searchAccountDto.authority, searchAccountDto.major) } returns listOf(account)
        every { outingBlackListRepository.findAll() } returns outingBlackListIdx.map { AnyValueObjectGenerator.anyValueObject("accountIdx" to it) }
        every { outingRepository.findAllOutingAccountIdx() } returns outingAccounts

        When("계정 검색 요청을 하면") {
            val result = searchAccountUseCase.execute(searchAccountDto)

            Then("result와 예상된 AccountDto 리스트는 같아야 한다.") {
                result shouldBe listOf(expectedAccountDto)
            }
        }
    }
})