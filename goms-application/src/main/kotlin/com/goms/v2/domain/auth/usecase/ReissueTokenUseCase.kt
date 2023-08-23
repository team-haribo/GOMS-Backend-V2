package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.ExpiredRefreshTokenException
import com.goms.v2.domain.auth.exception.InvalidTokenTypeException
import com.goms.v2.domain.auth.spi.TokenParserPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.RefreshTokenRepository

@UseCaseWithTransaction
class ReissueTokenUseCase(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accountRepository: AccountRepository,
    private val tokenPort: TokenPort,
    private val tokenParserPort: TokenParserPort
) {

    fun execute(refreshToken: String): TokenDto {
        val parsedRefreshToken = tokenParserPort.parseRefreshToken(refreshToken) ?: throw InvalidTokenTypeException()
        val refreshTokenDomain = refreshTokenRepository.findByIdOrNull(parsedRefreshToken) ?: throw ExpiredRefreshTokenException()
        val account = accountRepository.findByIdOrNull(refreshTokenDomain.accountIdx) ?: throw AccountNotFoundException()
        val token = tokenPort.generateToken(refreshTokenDomain.accountIdx, account.authority)
        saveRefreshToken(token, account)
        refreshTokenRepository.deleteById(parsedRefreshToken)

        return token
    }

    private fun saveRefreshToken(token: TokenDto, account: Account) {
        val saveRefreshToken = RefreshToken(
            refreshToken = token.refreshToken,
            accountIdx = account.idx,
            expiredAt = token.refreshTokenExp
        )

        refreshTokenRepository.save(saveRefreshToken)
    }

}