package com.goms.v2.domain.outing

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.domain.outing.usecase.SearchOutingAccountUseCase
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalTime
import java.util.*

class SearchOutingAccountUseCaseTest: BehaviorSpec({

    val outingRepository = mockk<OutingRepository>()
    val searchOutingAccountUseCase = SearchOutingAccountUseCase(outingRepository)

    Given("외출자 이름을 검색했을때") {
        val createdTime = LocalTime.now()
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val outingList = listOf(AnyValueObjectGenerator.anyValueObject<Outing>("account" to account, "createdTime" to createdTime))
        val name = ""
        val outingAccountDto = AnyValueObjectGenerator.anyValueObject<OutingAccountDto>("accountIdx" to accountIdx, "name" to name, "createdTime" to createdTime)

        every { outingRepository.findAll() } returns outingList
        every { outingRepository.findByAccountNameContaining(name) } returns outingList
        every { outingRepository.findByAccountNameContaining(null) } returns outingList

        When("이름이 null 이면") {
            val result = searchOutingAccountUseCase.execute(null)

            Then("외출중인 모든 학생이 검색되야 한다.") {
                result shouldBe listOf(outingAccountDto)
            }
        }

        When("외출자 이름을 요청 하면") {
            val result = searchOutingAccountUseCase.execute(name)

            Then("result와 outingDto는 같아야 한다.") {
                result shouldBe listOf(outingAccountDto)
            }
        }
    }
})