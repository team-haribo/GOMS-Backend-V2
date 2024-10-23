package com.goms.v2.domain.outing

import com.goms.v2.domain.outing.usecase.ValidateOutingTimeUseCase
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.LocalDate
import java.time.LocalTime

class ValidateOutingTimeUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val outingUseCase = ValidateOutingTimeUseCase()

    fun beforeTest() {
        mockkStatic(LocalDate::class)
        mockkStatic(LocalTime::class)
    }

    fun afterTest() {
        unmockkStatic(LocalDate::class)
        unmockkStatic(LocalTime::class)
    }

    Given("ValidateOutingTimeUseCase를 실행했을때 ") {

        When("수요일이며 18:50에서 19:25 사이일 때") {
            val wednesday = LocalDate.of(2023, 8, 16)
            val startTime = LocalTime.of(18, 55)

            beforeTest()

            every { LocalDate.now() } returns wednesday
            every { LocalTime.now() } returns startTime

            val result = outingUseCase.execute()

            Then("true를 반환해야 합니다.") {
                result shouldBe true
            }

            afterTest()

        }

        When("수요일이 아니거나 18:50에서 19:25 사이가 아닐 때") {
            val tuesday = LocalDate.of(2023, 8, 17)
            val timeBeforeStart = LocalTime.of(18, 45)
            val timeAfterEnd = LocalTime.of(19, 30)

            beforeTest()

            every { LocalDate.now() } returns tuesday
            every { LocalTime.now() } returns timeBeforeStart andThen timeAfterEnd

            val result = outingUseCase.execute()

            Then("false를 반환해야 합니다.") {
                result shouldBe false
            }

            afterTest()
        }
    }
})
