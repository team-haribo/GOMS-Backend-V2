package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.auth.data.dto.SendAuthCodeDto
import com.goms.v2.domain.auth.data.event.CreateAuthenticationEvent
import com.goms.v2.domain.auth.spi.NotificationSendPort
import com.goms.v2.domain.auth.usecase.SendAuthCodeUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class SendAuthCodeUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val notificationSendPort = mockk<NotificationSendPort>()
    val authenticationRepository = mockk<AuthenticationRepository>()
    val authCodeRepository = mockk<AuthCodeRepository>()
    val publisher = mockk<ApplicationEventPublisher>()
    val accountRepository = mockk<AccountRepository>()
    val sendAuthCodeUseCase = SendAuthCodeUseCase(notificationSendPort, authenticationRepository, authCodeRepository, publisher, accountRepository)

    Given("SendAuthCodeDto가 주어질 때") {
        val email = ""
        val dto = AnyValueObjectGenerator.anyValueObject<SendAuthCodeDto>("email" to email)
        val authentication = AnyValueObjectGenerator.anyValueObject<Authentication>("email" to dto.email, "expiredAt" to 300L)
        val createAuthenticationEvent = AnyValueObjectGenerator.anyValueObject<CreateAuthenticationEvent>("authentication" to authentication )
        val increaseCreateAuthenticationEvent = AnyValueObjectGenerator.anyValueObject<CreateAuthenticationEvent>("authentication" to authentication.increaseAttemptCount())

        every { authenticationRepository.existByEmail(dto.email) } returns false
        every { notificationSendPort.sendNotification(dto.email, any()) } returns Unit
        every { authCodeRepository.save(any<AuthCode>()) } returns Unit
        every { publisher.publishEvent(createAuthenticationEvent) } returns Unit

        When("처음 인증번호 요청을 한다면") {
            sendAuthCodeUseCase.execute(dto)

            Then("인증번호가 전송되고 authentication이 저장되는 이벤트가 발생해야 한다.") {
                verify(exactly = 1) { authenticationRepository.existByEmail(dto.email) }
                verify(exactly = 1) { notificationSendPort.sendNotification(dto.email, any()) }
                verify(exactly = 1) { authCodeRepository.save(any<AuthCode>()) }
                verify(exactly = 1) { publisher.publishEvent(createAuthenticationEvent) }
            }
        }

        When("그전에 인증번호를 요청한 이력이 있으면") {
            every { authenticationRepository.existByEmail(dto.email) } returns true
            every { authenticationRepository.findByIdOrNull(dto.email) } returns authentication
            every { publisher.publishEvent(increaseCreateAuthenticationEvent) } returns Unit

            sendAuthCodeUseCase.execute(dto)

            Then("인증번호가 전송되고 시도 횟수를 증가시킨 authentication이 저장되는 이벤트가 발생해야 한다. ") {
                verify(exactly = 1) { authenticationRepository.existByEmail(dto.email) }
                verify(exactly = 1) { authenticationRepository.findByIdOrNull(dto.email) }
                verify(exactly = 1) { publisher.publishEvent(increaseCreateAuthenticationEvent) }
                verify(exactly = 1) { authCodeRepository.save(any<AuthCode>()) }
            }
        }

    }
})
