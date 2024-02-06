package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.InvalidTokenTypeException
import com.goms.v2.domain.auth.spi.TokenParsePort
import com.goms.v2.repository.auth.RefreshTokenRepository

@UseCaseWithTransaction
class LogoutUseCase(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenParsePort: TokenParsePort
) {

    fun execute(refreshToken: String) {
        val parsedRefreshToken = tokenParsePort.parseRefreshToken(refreshToken) ?: throw InvalidTokenTypeException()
        refreshTokenRepository.findByIdOrNull(parsedRefreshToken)
    }
}