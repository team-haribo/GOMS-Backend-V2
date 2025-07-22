package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.data.dto.ChangePasswordDto
import com.goms.v2.domain.account.exception.DuplicatedNewPasswordException
import com.goms.v2.domain.account.updatePassword
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository

@UseCaseWithTransaction
class ChangePasswordUseCase(
    private val accountRepository: AccountRepository,
    private val passwordEncoderPort: PasswordEncoderPort,
    private val accountUtil: AccountUtil
) {

    fun execute(changePasswordDto: ChangePasswordDto) {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()

        if (!passwordEncoderPort.isPasswordMatch(changePasswordDto.password, account.password)) {
            throw PasswordNotMatchException()
        }

        if (passwordEncoderPort.isPasswordMatch(changePasswordDto.newPassword, account.password)) {
            throw DuplicatedNewPasswordException()
        }

        account.updatePassword(passwordEncoderPort.passwordEncode(changePasswordDto.newPassword))
        accountRepository.save(account)
    }
}