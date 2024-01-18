package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.data.event.SaveRefreshTokenEvent
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.repository.account.AccountRepository
import org.springframework.context.ApplicationEventPublisher

@UseCaseWithTransaction
class SignInUseCase(
    private val accountRepository: AccountRepository,
    private val tokenPort: TokenPort,
    private val passwordEncoderPort: PasswordEncoderPort,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(signInDto: SignInDto): TokenDto {
        val account = accountRepository.findByEmail(signInDto.email)
            ?: throw AccountNotFoundException()

        if (!passwordEncoderPort.isPasswordMatch(signInDto.password, account.password)) {
            throw PasswordNotMatchException()
        }

        val token = tokenPort.generateToken(account.idx, account.authority)

        publishSaveRefreshToken(token, account)

        return token
    }

    private fun publishSaveRefreshToken(token: TokenDto, account: Account) {
        val saveRefreshTokenEvent = SaveRefreshTokenEvent(
            refreshToken = token.refreshToken,
            accountIdx = account.idx,
            expiredAt = token.refreshTokenExp
        )
        publisher.publishEvent(saveRefreshTokenEvent)
    }

}