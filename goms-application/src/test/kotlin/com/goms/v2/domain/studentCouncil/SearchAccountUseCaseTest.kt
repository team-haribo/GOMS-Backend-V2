package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.outing.OutingBlackList
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.SearchAccountDto
import com.goms.v2.domain.studentCouncil.usecase.SearchAccountUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.UUID

class SearchAccountUseCaseTest: BehaviorSpec({
    val accountRepository = mockk<AccountRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val searchAccountUseCase = SearchAccountUseCase(accountRepository, outingBlackListRepository)

    Given("계정 검색 키워드가 주어질때") {
        val searchAccountDto = SearchAccountDto(
            grade = 0,
            gender = Gender.MAN,
            name = "",
            authority = Authority.ROLE_STUDENT,
            isBlackList = true
        )
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val accountDto = AccountDto(
            accountIdx = accountIdx,
            name = "",
            grade = 6,
            gender = Gender.MAN,
            major = Major.SMART_IOT,
            profileUrl = "",
            authority = Authority.ROLE_STUDENT,
            isBlackList = true
        )
        val outingBlackList = AnyValueObjectGenerator.anyValueObject<OutingBlackList>("accountIdx" to account.idx)

        every { accountRepository.findAccountByStudentInfo(searchAccountDto.grade, searchAccountDto.gender, searchAccountDto.name, searchAccountDto.authority) } returns listOf(account)
        every { outingBlackListRepository.findAll() } returns listOf(outingBlackList)

        When("계정 검색 요청을 하면") {
            val result = searchAccountUseCase.execute(searchAccountDto)

            Then("result와 accountDto는 같아야 한다.") {
                result shouldBe listOf(accountDto)
            }
        }
    }
})
