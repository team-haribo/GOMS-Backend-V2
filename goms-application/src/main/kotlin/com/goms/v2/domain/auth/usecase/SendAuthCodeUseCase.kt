package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.domain.auth.Authentication
import com.goms.v2.domain.auth.EmailStatus
import com.goms.v2.domain.auth.data.dto.SendAuthCodeDto
import com.goms.v2.domain.auth.data.event.CreateAuthenticationEvent
import com.goms.v2.domain.auth.exception.AlreadyExistEmailException
import com.goms.v2.domain.auth.exception.EmailNotFoundException
import com.goms.v2.domain.auth.exception.ManyEmailRequestException
import com.goms.v2.domain.auth.spi.NotificationSendPort
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@UseCaseWithTransaction
class SendAuthCodeUseCase(
    private val notificationSendPort: NotificationSendPort,
    private val authenticationRepository: AuthenticationRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val publisher: ApplicationEventPublisher,
    private val accountRepository: AccountRepository
) {

    fun execute(dto: SendAuthCodeDto) {
        if (dto.emailStatus == EmailStatus.AFTER_SIGNUP) {
            if (!accountRepository.existsByEmail(dto.email))
                throw EmailNotFoundException()
        }


        val isExistsAuthentication = authenticationRepository.existByEmail(dto.email)

        if (isExistsAuthentication) {
            val authentication = authenticationRepository.findByIdOrNull(dto.email)
            if (authentication!!.attemptCount > 5) {
                throw ManyEmailRequestException()
            }

            publisher.publishEvent(CreateAuthenticationEvent(authentication.increaseAttemptCount()))
        }

        val code = createCode()
        notificationSendPort.sendNotification(dto.email, code)
        val authCode = AuthCode(
            email = dto.email,
            authCode = code,
            expiredAt = 300
        )
        authCodeRepository.save(authCode)

        if (!isExistsAuthentication) {
            val authentication = Authentication(
                email = dto.email,
                attemptCount = 0,
                authCodeCount = 0,
                isAuthentication = false,
                expiredAt = Authentication.EXPIRED_AT
            )
            publisher.publishEvent(CreateAuthenticationEvent(authentication))
        }
    }

    private fun createCode() = Random().nextInt(8888).plus(1111).toString()

}