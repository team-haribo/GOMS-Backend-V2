package com.goms.v2.domain.late

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.late.usecase.SaveLateAccountUseCase
import com.goms.v2.domain.outing.Outing
import com.goms.v2.repository.late.LateRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class SaveLateAccountUseCaseTest: BehaviorSpec({
    val outingRepository = mockk<OutingRepository>()
    val lateRepository = mockk<LateRepository>()
    val outingBlackListRepository = mockk<OutingBlackListRepository>()
    val saveLateAccountUseCase = SaveLateAccountUseCase(outingRepository, lateRepository, outingBlackListRepository)

    Given("지각생이 생길때") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val outingList = listOf(AnyValueObjectGenerator.anyValueObject<Outing>("account" to account))

        every { outingRepository.findAll() } returns outingList
        every { lateRepository.saveAll(any()) } returns Unit
        every { outingBlackListRepository.saveAll(any()) } returns Unit

        When("지각생을 저장하는 요청을 하면") {
            saveLateAccountUseCase.execute()

            Then("지각생을 저장하는 요청을 하면") {
                verify(exactly = 1) { lateRepository.saveAll(any()) }
            }

            Then("외출 블랙리스트 테이블에 저장되어야 한다.") {
                verify(exactly = 1) { outingBlackListRepository.saveAll(any())}
            }
        }
    }
})