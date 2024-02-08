package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.late.Late
import com.goms.v2.domain.studentCouncil.data.dto.LateAccountDto
import com.goms.v2.domain.studentCouncil.usecase.GetLateAccountUseCase
import com.goms.v2.repository.late.LateRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

class GetLateAccountUseCaseTest: BehaviorSpec({
    val lateRepository = mockk<LateRepository>()
    val getLateAccountUseCase = GetLateAccountUseCase(lateRepository)

    Given("LocalDate가 주어질때") {
        val date = LocalDate.parse("2024-02-08")
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val late = Late(
            idx = 0L,
            account = account,
            createdTime = date
        )
        val lateAccountDto = AnyValueObjectGenerator.anyValueObject<LateAccountDto>("accountIdx" to accountIdx)

        every { lateRepository.findAllByCreatedTime(date) } returns listOf(late)

        When("지각자 리스트를 요청하면") {
            val result = getLateAccountUseCase.execute(date)

            Then("result와 lateAccountDto는 같아야 한다.") {
                result shouldBe listOf(lateAccountDto)
            }
        }
    }
})