package com.goms.v2.domain.outing

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.domain.outing.usecase.QueryOutingAccountUseCase
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalTime
import java.util.*

class QueryOutingAccountUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val outingRepository = mockk<OutingRepository>()
    val queryOutingAccountUseCase = QueryOutingAccountUseCase(outingRepository)

    Given("외출자가 생길때") {
        val createdTime = LocalTime.now()
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val outing = AnyValueObjectGenerator.anyValueObject<Outing>("account" to account, "createdTime" to createdTime)
        val outingAccountDto = AnyValueObjectGenerator.anyValueObject<OutingAccountDto>("accountIdx" to accountIdx, "createdTime" to createdTime)

        every { outingRepository.findAllByOrderByCreatedTimeDesc() } returns listOf(outing)

        When("외출자 목록 요청을 하면") {
            val result = queryOutingAccountUseCase.execute()

            Then("result와 outingAccountDto은 같아야 한다.") {
                result shouldBe listOf(outingAccountDto)
            }
        }
    }
})
