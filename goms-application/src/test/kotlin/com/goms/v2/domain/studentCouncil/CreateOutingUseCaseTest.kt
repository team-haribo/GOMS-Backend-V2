package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.property.studentCouncil.OutingUUIDExpTimeProperties
import com.goms.v2.domain.studentCouncil.usecase.CreateOutingUseCase
import com.goms.v2.repository.studentCouncil.OutingUUIDRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class CreateOutingUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val outingUUIDRepository = mockk<OutingUUIDRepository>()
    val outingUUIDExpTimeProperties = mockk<OutingUUIDExpTimeProperties>(relaxed = true)
    val createOutingUseCase = CreateOutingUseCase(outingUUIDRepository, outingUUIDExpTimeProperties)

    Given("outingUUID가 주어질때") {
        val outingUUID = AnyValueObjectGenerator.anyValueObject<OutingUUID>("outingUUID" to UUID.randomUUID())

        every { outingUUIDRepository.save(any()) } returns outingUUID.outingUUID

        When("외출 식별자 저장 요청을 하면") {
            val result = createOutingUseCase.execute()

            Then("외출 UUID는 redis에 저장되어야 한다.") {
                verify(exactly = 1) { outingUUIDRepository.save(any()) }
            }

            Then("result와 outingUUID는 같아야 한다.") {
                result shouldBe outingUUID.outingUUID
            }
        }
    }
})
