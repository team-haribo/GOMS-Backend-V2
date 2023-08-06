package com.goms.v2.domain.email.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.email.AuthCode
import com.goms.v2.domain.email.Authentication
import com.goms.v2.domain.email.data.dto.EmailDto
import com.goms.v2.domain.email.exception.ManyEmailRequestException
import com.goms.v2.domain.email.spi.JavaMailSendPort
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.email.AuthCodeRepository
import com.goms.v2.repository.email.AuthenticationRepository

@UseCaseWithTransaction
class SendEmailUseCase(
    private val javaMailSendPort: JavaMailSendPort,
    private val accountRepository: AccountRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    fun execute(emailDto: EmailDto) {
        val isExistsAuthentication = authenticationRepository.existByEmail(emailDto.email)
        if (isExistsAuthentication) {
            val authentication = authenticationRepository.findByIdOrNull(emailDto.email)
            if(authentication.attemptCount > 5) {
                throw ManyEmailRequestException()
            }
            authentication.updateAuthentication(true)
            authentication.increaseAttemptCount()
            authenticationRepository.save(authentication)
        }

        val authCode = generateAuthKey(9999)
        if (!accountRepository.existsByEmail(emailDto.email)) {
            throw AccountNotFoundException()
        }
        val authCodeDomain = authCodeRepository.findByIdOrNull(emailDto.email)
            ?: AuthCode(emailDto.email,authCode,300)
        authCodeDomain.updateAuthCode(authCode)
        authCodeRepository.save(authCodeDomain)

        javaMailSendPort.sendEmail(emailDto.email, authCode)
        if (!isExistsAuthentication) {
            val authentication = Authentication(emailDto.email,0,true,300)
            authenticationRepository.save(authentication)
        }

    }

    private fun generateAuthKey(number: Int = 9999) = (0..number).random()
        .toString()
        .padStart(number.toString().length, '0')

}