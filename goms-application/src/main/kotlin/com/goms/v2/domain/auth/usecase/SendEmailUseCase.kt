package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.domain.auth.Authentication
import com.goms.v2.domain.auth.data.dto.EmailDto
import com.goms.v2.domain.auth.exception.ManyEmailRequestException
import com.goms.v2.domain.auth.spi.EmailSendPort
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository

@UseCaseWithTransaction
class SendEmailUseCase(
    private val emailSendPort: EmailSendPort,
    private val accountRepository: AccountRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    fun execute(emailDto: EmailDto) {
        val account = accountRepository.findByEmail(emailDto.email) ?: throw AccountNotFoundException()
        val isExistsAuthentication = authenticationRepository.existByEmail(account.email)
        if (isExistsAuthentication) saveAuthentication(account.email)
        if (!isExistsAuthentication) createAuthentication(account.email)
        val authCode = generateAuthKey(9999)
        saveAuthCode(emailDto, authCode)
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
        authentication.increaseAttemptCount()
        authenticationRepository.save(authentication)
    }

    private fun createAuthentication(email: String) {
        val authentication = Authentication(
            email = email,
            attemptCount = 1,
            authCodeCount = 0,
            isAuthentication = false,
            expiredAt = 300
        )
        authenticationRepository.save(authentication)
    }

    private fun saveAuthCode(emailDto: EmailDto, authCode: String) {
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