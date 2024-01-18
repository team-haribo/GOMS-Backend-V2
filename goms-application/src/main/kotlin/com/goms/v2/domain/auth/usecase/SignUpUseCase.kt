package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.exception.AlreadyExistPhoneNumberException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository
import java.time.LocalDateTime
import java.util.*

@UseCaseWithTransaction
class SignUpUseCase(
    private val accountRepository: AccountRepository,
    private val passwordEncoderPort: PasswordEncoderPort
) {

    fun execute(signUpDto: SignUpDto) {
        if (accountRepository.existsByPhoneNumber(signUpDto.phoneNumber))
            throw AlreadyExistPhoneNumberException()

        val account = Account(
            idx = UUID.randomUUID(),
            phoneNumber = signUpDto.phoneNumber,
            password = passwordEncoderPort.passwordEncode(signUpDto.password),
            grade = signUpDto.grade,
            gender = signUpDto.gender,
            name = signUpDto.name,
            profileUrl = null,
            authority = Authority.ROLE_STUDENT,
            createdTime = LocalDateTime.now()
        )

        accountRepository.save(account)

    }
}