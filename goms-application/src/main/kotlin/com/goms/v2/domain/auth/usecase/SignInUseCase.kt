package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.spi.GAuthPort
import com.goms.v2.common.spi.JwtPort
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.StudentNumber
import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.domain.auth.dto.request.SignInRequest
import com.goms.v2.domain.auth.dto.response.TokenInResponse
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.RefreshTokenRepository
import gauth.GAuth
import gauth.GAuthUserInfo
import gauth.exception.GAuthException
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.*

private val log = KotlinLogging.logger { }

@UseCaseWithTransaction
class SignInUseCase(
    private val gAuth: GAuth,
    private val accountRepository: AccountRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val gAuthPort: GAuthPort,
    private val jwtPort: JwtPort
) {

    fun execute(dto: SignInRequest): TokenInResponse {
        runCatching {
            gAuthPort.receiveGAuthToken(dto.code)
        }.onFailure {
            throw GAuthException(500)
        }.onSuccess {
            log.info { "GAuth Token is ${it.accessToken}" }
            val gAuthInfo = gAuth.getUserInfo(it.accessToken)
            log.info { "GAuth email is ${gAuthInfo.email}" }
            val account = accountRepository.findByEmail(gAuthInfo.email) ?: saveAccount(gAuthInfo)
            val (accessToken, refreshToken, accessTokenExp, refreshTokenExp) = jwtPort.generateToken(account.idx, account.authority)
            refreshTokenRepository.save(
                RefreshToken(
                    refreshToken = refreshToken,
                    accountIdx = account.idx,
                    )
            )
            return TokenInResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
                accessTokenExp = accessTokenExp,
                refreshTokenExp = refreshTokenExp,
                authority = account.authority,
            )
        }

        throw GAuthException(500)
    }

    private fun saveAccount(gAuthInfo: GAuthUserInfo): Account {
        val account = Account(
            idx = UUID.randomUUID(),
            email = gAuthInfo.email,
            name = gAuthInfo.name,
            studentNumber = StudentNumber(grade = gAuthInfo.grade, classNum = gAuthInfo.classNum, number = gAuthInfo.num),
            profileUrl = gAuthInfo.profileUrl,
            authority = Authority.ROLE_STUDENT,
            createdTime = LocalDateTime.now()
        )
        accountRepository.save(account)
        return account
    }

}