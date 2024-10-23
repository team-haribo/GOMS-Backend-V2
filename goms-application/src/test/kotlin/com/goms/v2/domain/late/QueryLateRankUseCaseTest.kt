package com.goms.v2.domain.late

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.late.data.dto.LateRankDto
import com.goms.v2.domain.late.usecase.QueryLateRankUseCase
import com.goms.v2.repository.late.LateRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class QueryLateRankUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val lateRepository = mockk<LateRepository>()
    val queryLateRankUseCase = QueryLateRankUseCase(lateRepository)

    Given("지각 랭킹이 있을때") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val late = AnyValueObjectGenerator.anyValueObject<Late>("account" to account)
        val lateLankList = AnyValueObjectGenerator.anyValueObject<LateRankDto>("accountIdx" to accountIdx)

        every { lateRepository.findTop3ByOrderByAccountDesc() } returns listOf(late)

        When("지각 랭킹 조회를 요청하면") {
            val result = queryLateRankUseCase.execute()

            Then("result와 List<Late>는 같아야 한다.") {
                result shouldBe listOf(lateLankList)
            }
        }

    }
})
