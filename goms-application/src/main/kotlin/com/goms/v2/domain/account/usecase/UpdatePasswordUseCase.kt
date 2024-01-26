package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AuthenticationValidator
import com.goms.v2.domain.account.data.dto.PasswordDto
import com.goms.v2.domain.account.exception.DuplicatedNewPasswordException
import com.goms.v2.domain.account.updatePassword
import com.goms.v2.domain.auth.data.event.DeleteAuthenticationEvent
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository
import org.springframework.context.ApplicationEventPublisher

@UseCaseWithTransaction
class UpdatePasswordUseCase(
    private val accountRepository: AccountRepository,
    private val authenticationValidator: AuthenticationValidator,
    private val passwordEncoderPort: PasswordEncoderPort,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(passwordDto: PasswordDto) {
        val account = accountRepository.findByEmail(passwordDto.email)
            ?: throw AccountNotFoundException()

        if (passwordEncoderPort.isPasswordMatch(passwordDto.newPassword, account.password)) {
            throw DuplicatedNewPasswordException()
        }

        val authentication = authenticationValidator.verifyAuthenticationByEmail(passwordDto.email)
        publisher.publishEvent(DeleteAuthenticationEvent(authentication))

        account.updatePassword(passwordEncoderPort.passwordEncode(passwordDto.newPassword))
        accountRepository.save(account)
    }

}