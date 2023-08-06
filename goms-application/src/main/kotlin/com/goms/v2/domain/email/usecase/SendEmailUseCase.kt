package com.goms.v2.domain.email.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.email.AuthCode
import com.goms.v2.domain.email.Authentication
import com.goms.v2.domain.email.data.dto.EmailDto
import com.goms.v2.domain.email.exception.ManyEmailRequestException
import com.goms.v2.domain.email.spi.EmailSendPort
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.email.AuthCodeRepository
import com.goms.v2.repository.email.AuthenticationRepository

@UseCaseWithTransaction
class SendEmailUseCase(
    private val emailSendPort: EmailSendPort,
    private val accountRepository: AccountRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    fun execute(emailDto: EmailDto) {
        val isExistsAuthentication = authenticationRepository.existByEmail(emailDto.email)
        if (isExistsAuthentication) saveAuthentication(emailDto.email)
        if (!isExistsAuthentication) createAuthentication(emailDto.email)
        val authCode = generateAuthKey(9999)
        saveAuthCodeRepository(emailDto, authCode)
        emailSendPort.sendEmail(emailDto.email, authCode)
    }

    private fun generateAuthKey(number: Int = 9999) = (0..number).random()
        .toString()
        .padStart(number.toString().length, '0')

    private fun saveAuthentication(email: String) {
        val authentication = authenticationRepository.findByIdOrNull(email)
        if(authentication!!.attemptCount >= 5) {
            throw ManyEmailRequestException()
        }
        authentication.certified()
        authentication.increaseAttemptCount()
        authenticationRepository.save(authentication)
    }

    private fun createAuthentication(email: String) {
        val authentication = Authentication(
            email = email,
            attemptCount = 1,
            isAuthentication = true,
            expiredAt = 300
        )
        authenticationRepository.save(authentication)
    }

    private fun saveAuthCodeRepository(emailDto: EmailDto, authCode: String) {
        if (!accountRepository.existsByEmail(emailDto.email)) {
            throw AccountNotFoundException()
        }
        val authCodeDomain = authCodeRepository.findByIdOrNull(emailDto.email)
            ?: AuthCode(
                email = emailDto.email,
                authCode = authCode,
                expiredAt = 300
            )
        authCodeDomain.updateAuthCode(authCode)
        authCodeRepository.save(authCodeDomain)
    }

}