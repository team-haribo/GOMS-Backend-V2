package com.goms.v2.domain.email.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.email.data.dto.EmailDto
import com.goms.v2.domain.email.spi.EmailPort
import com.goms.v2.repository.email.EmailAuthRepository

@UseCaseWithTransaction
class SendEmailUseCase(
    private val emailAuthRepository: EmailAuthRepository,
    private val emailPort: EmailPort
) {

    fun execute(emailDto: EmailDto) {
        val authKey = generateCertificationNumber(9999)
        if (!emailAuthRepository.existByEmail(emailDto.email)) throw AccountNotFoundException()
        emailPort.sendEmail(emailDto.email, authKey)
    }

    fun generateCertificationNumber(number: Int = 9999) = (0..number).random()
        .toString()
        .padStart(number.toString().length, '0')

}