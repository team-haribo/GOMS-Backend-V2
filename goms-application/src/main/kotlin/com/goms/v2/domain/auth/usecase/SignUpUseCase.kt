package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AuthenticationValidator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.data.event.DeleteAuthenticationEvent
import com.goms.v2.domain.auth.exception.AlreadyExistEmailException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository
import org.springframework.context.ApplicationEventPublisher
import java.time.LocalDateTime
import java.util.*

@UseCaseWithTransaction
class SignUpUseCase(
    private val accountRepository: AccountRepository,
    private val passwordEncoderPort: PasswordEncoderPort,
    private val authenticationValidator: AuthenticationValidator,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(signUpDto: SignUpDto) {
        if (accountRepository.existsByEmail(signUpDto.email))
            throw AlreadyExistEmailException()

        val authentication = authenticationValidator.verifyAuthenticationByEmail(signUpDto.email)
        val deleteAuthenticationEvent = DeleteAuthenticationEvent(authentication)
        publisher.publishEvent(deleteAuthenticationEvent)

        val account = Account(
            idx = UUID.randomUUID(),
            email = signUpDto.email,
            password = passwordEncoderPort.passwordEncode(signUpDto.password),
            grade = extractGrade(signUpDto.email)!!,
            gender = signUpDto.gender,
            major = signUpDto.major,
            name = signUpDto.name,
            profileUrl = null,
            authority = Authority.ROLE_STUDENT,
            createdTime = LocalDateTime.now()
        )
        accountRepository.save(account)

    }

    private fun extractGrade(email: String): Int {
        val admissionYear = email.substring(1, 3).toInt() + 2000
        return admissionYear - 2017 + 1
    }
}