package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.data.event.CreateAuthenticationEvent
import com.goms.v2.domain.auth.exception.AuthCodeNotFoundException
import com.goms.v2.domain.auth.exception.AuthCodeNotMatchException
import com.goms.v2.domain.auth.exception.AuthenticationNotFoundException
import com.goms.v2.domain.auth.exception.TooManyAuthCodeRequestException
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository
import org.springframework.context.ApplicationEventPublisher

@UseCaseWithTransaction
class VerifyAuthCodeUseCase(
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(email: String, authCode: String) {
        val authCodeDomain = authCodeRepository.findByIdOrNull(email)
            ?: throw AuthCodeNotFoundException()
        val authentication = authenticationRepository.findByIdOrNull(email)
            ?: throw AuthenticationNotFoundException()

        if (authentication.authCodeCount > 5) throw TooManyAuthCodeRequestException()

        if (authCodeDomain.authCode != authCode) {
            publisher.publishEvent(CreateAuthenticationEvent(authentication.increaseAuthCodeCount()))
            throw AuthCodeNotMatchException()
        }

        publisher.publishEvent(CreateAuthenticationEvent(authentication.certified()))
    }

}