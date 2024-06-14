package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.domain.auth.Authentication
import com.goms.v2.domain.auth.data.dto.SendAuthCodeDto
import com.goms.v2.domain.auth.data.event.CreateAuthenticationEvent
import com.goms.v2.domain.auth.exception.ManyEmailRequestException
import com.goms.v2.domain.auth.spi.EmailSendPort
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@UseCaseWithTransaction
class SendAuthCodeUseCase(
    private val emailSendPort: EmailSendPort,
    private val authenticationRepository: AuthenticationRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(dto: SendAuthCodeDto) {
        val isExistsAuthentication = authenticationRepository.existByEmail(dto.email)

        if (isExistsAuthentication) {
            val authentication = authenticationRepository.findByIdOrNull(dto.email)
            if (authentication!!.attemptCount > 5) {
                throw ManyEmailRequestException()
            }

            publisher.publishEvent(CreateAuthenticationEvent(authentication.increaseAttemptCount()))
        }

        val code = createCode()
        emailSendPort.sendEmail(dto.email, code)
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