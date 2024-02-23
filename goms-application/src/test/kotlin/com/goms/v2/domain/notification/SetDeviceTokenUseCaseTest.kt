package com.goms.v2.domain.notification

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.notification.usecase.SetDeviceTokenUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.notification.DeviceTokenRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class SetDeviceTokenUseCaseTest: BehaviorSpec({
    val accountUtil = mockk<AccountUtil>()
    val accountRepository = mockk<AccountRepository>()
    val deviceTokenRepository = mockk<DeviceTokenRepository>()
    val setDeviceTokenUseCase = SetDeviceTokenUseCase(accountUtil, accountRepository, deviceTokenRepository)

    Given("알림 타입이 생기면") {
        val accountIdx = UUID.randomUUID()
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val deviceToken = DeviceToken(
            accountIdx = accountIdx,
            token = "token"
        )

        every { accountUtil.getCurrentAccountIdx() } returns accountIdx
        every { accountRepository.findByIdOrNull(accountIdx) } returns account
        every { deviceTokenRepository.save(deviceToken) } returns Unit

        When("토큰을 저장하는 요청을 하면") {
            setDeviceTokenUseCase.execute("token")

            Then("device token을 저장해야합니다.") {
                verify(exactly = 1) { deviceTokenRepository.save(deviceToken)}
            }
        }
        When("없는 계정 idx일 경우") {
            every { accountRepository.findByIdOrNull(accountIdx) } returns null

            Then("AccountNotFound가 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    setDeviceTokenUseCase.execute("token")
                }
            }
        }
    }
})