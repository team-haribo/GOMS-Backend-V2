package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.data.event.SaveRefreshTokenEvent
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.auth.exception.AuthCodeNotFoundException
import com.goms.v2.domain.auth.exception.AuthCodeNotMatchException
import com.goms.v2.domain.auth.exception.AuthenticationNotFoundException
import com.goms.v2.domain.auth.exception.TooManyAuthCodeRequestException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository
import org.springframework.context.ApplicationEventPublisher

@UseCaseWithTransaction
class VerifyAuthCodeUseCase(
    private val accountRepository: AccountRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val tokenPort: TokenPort,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(email:String, authCode: String): TokenDto {
        val authCodeDomain = authCodeRepository.findByIdOrNull(email) ?: throw AuthCodeNotFoundException()
        val authenticationDomain = authenticationRepository.findByIdOrNull(email) ?: throw AuthenticationNotFoundException()
        val account = accountRepository.findByEmail(email) ?: throw AccountNotFoundException()
        if (authenticationDomain.authCodeCount > 5) throw TooManyAuthCodeRequestException()

        if (authCodeDomain.authCode != authCode) {
            authenticationDomain.increaseAuthCodeCount()
            authenticationRepository.save(authenticationDomain)
            throw AuthCodeNotMatchException()
        }

        val token = tokenPort.generateToken(account.idx, account.authority)
        publishSaveRefreshToken(token, account)

        authenticationDomain.certified()
        authenticationRepository.save(authenticationDomain)

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