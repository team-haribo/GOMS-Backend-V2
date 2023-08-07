package com.goms.v2.domain.email.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.email.exception.AuthCodeNotFoundException
import com.goms.v2.domain.email.exception.AuthCodeNotMatchException
import com.goms.v2.domain.email.exception.AuthenticationNotFoundException
import com.goms.v2.domain.email.exception.TooManyAuthCodeRequestException
import com.goms.v2.repository.email.AuthCodeRepository
import com.goms.v2.repository.email.AuthenticationRepository

@UseCaseWithTransaction
class VerifyAuthCodeUseCase(
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    fun execute(email:String, authCode: String) {
        val authCodeDomain = authCodeRepository.findByIdOrNull(email)
            ?: throw AuthCodeNotFoundException()
        val authenticationDomain = authenticationRepository.findByIdOrNull(email)
            ?: throw AuthenticationNotFoundException()

        if (authenticationDomain.authCodeCount > 5) throw TooManyAuthCodeRequestException()

        if (authCodeDomain.authCode != authCode) {
            authenticationDomain.increaseAuthCodeCount()
            authenticationRepository.save(authenticationDomain)
            throw AuthCodeNotMatchException()
        }

        authenticationDomain.certified()
        authenticationRepository.save(authenticationDomain)
    }
    
}