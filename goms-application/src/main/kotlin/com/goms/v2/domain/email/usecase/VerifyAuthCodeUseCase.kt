package com.goms.v2.domain.email.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.email.exception.AuthCodeNotFoundException
import com.goms.v2.domain.email.exception.AuthCodeNotMatchException
import com.goms.v2.domain.email.exception.AuthenticationNotFoundException
import com.goms.v2.domain.email.exception.TooManyAuthCodeRequestException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.RefreshTokenRepository
import com.goms.v2.repository.email.AuthCodeRepository
import com.goms.v2.repository.email.AuthenticationRepository

@UseCaseWithTransaction
class VerifyAuthCodeUseCase(
    private val accountRepository: AccountRepository,
    private val authCodeRepository: AuthCodeRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenPort: TokenPort
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
        saveRefreshToken(token, account)

        authenticationDomain.certified()
        authenticationRepository.save(authenticationDomain)

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