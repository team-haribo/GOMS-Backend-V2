package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.ExpiredRefreshTokenException
import com.goms.v2.domain.auth.exception.InvalidTokenTypeException
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.gloabl.event.SaveRefreshTokenEvent
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.RefreshTokenRepository
import org.springframework.context.ApplicationEventPublisher

@UseCaseWithTransaction
class ReissueTokenUseCase(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accountRepository: AccountRepository,
    private val publisher: ApplicationEventPublisher,
    private val tokenPort: TokenPort
) {

    fun execute(refreshToken: String): TokenDto {
        val parsedRefreshToken = tokenPort.parseRefreshToken(refreshToken)
            ?: throw InvalidTokenTypeException()
        val refreshTokenDomain = refreshTokenRepository.findByIdOrNull(parsedRefreshToken)
            ?: throw ExpiredRefreshTokenException()
        val account = accountRepository.findByIdOrNull(refreshTokenDomain.accountIdx)
            ?: throw AccountNotFoundException()
        val token = tokenPort.generateToken(refreshTokenDomain.accountIdx, account.authority)
        publishReissueRefreshToken(token, account)
        refreshTokenRepository.deleteByRefreshToken(parsedRefreshToken)

        return token
    }

    private fun publishReissueRefreshToken(token: TokenDto, account: Account) {
        val saveRefreshTokenEvent = SaveRefreshTokenEvent(
            refreshToken = token.refreshToken,
            accountIdx = account.idx,
            expiredAt = token.refreshTokenExp
        )
        publisher.publishEvent(saveRefreshTokenEvent)
    }

}