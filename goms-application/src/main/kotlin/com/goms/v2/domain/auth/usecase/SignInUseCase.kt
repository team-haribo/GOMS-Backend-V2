package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.gloabl.exception.exception.GomsException
import com.goms.v2.domain.spi.GAuthPort
import com.goms.v2.domain.spi.TokenPort
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.StudentNumber
import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.domain.auth.dto.GAuthUserInfoDto
import com.goms.v2.domain.auth.dto.request.SignInDto
import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.domain.auth.exception.ExpiredCodeException
import com.goms.v2.domain.auth.exception.InternalServerErrorException
import com.goms.v2.domain.auth.exception.SecretMismatchException
import com.goms.v2.domain.auth.exception.ServiceNotFoundException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.RefreshTokenRepository
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.util.*

private val log = KotlinLogging.logger { }

@UseCaseWithTransaction
class SignInUseCase(
    private val accountRepository: AccountRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val gAuthPort: GAuthPort,
    private val tokenPort: TokenPort
) {

    fun execute(dto: SignInDto): TokenDto {
        try {
            val gAuthToken = gAuthPort.receiveGAuthToken(dto.code)
            log.info { "GAuth Token is ${gAuthToken.accessToken}" }
            val gAuthInfo = gAuthPort.receiveUserInfo(gAuthToken.accessToken)
            log.info { "GAuth email is ${gAuthInfo.email}" }
            val account = accountRepository.findByEmail(gAuthInfo.email) ?: saveAccount(gAuthInfo)
            val (accessToken, refreshToken, accessTokenExp, refreshTokenExp) = tokenPort.generateToken(
                account.idx,
                account.authority
            )
            refreshTokenRepository.save(
                RefreshToken(
                    refreshToken = refreshToken,
                    accountIdx = account.idx,
                )
            )
            return TokenDto(
                accessToken = accessToken,
                refreshToken = refreshToken,
                accessTokenExp = accessTokenExp,
                refreshTokenExp = refreshTokenExp,
                authority = account.authority,
            )
        } catch (error: GomsException) {
            when (error.errorCode.status) {
                HttpStatus.BAD_REQUEST -> throw SecretMismatchException()
                HttpStatus.UNAUTHORIZED -> throw ExpiredCodeException()
                HttpStatus.NOT_FOUND -> throw ServiceNotFoundException()
                else -> throw InternalServerErrorException()
            }
        }
    }

    private fun saveAccount(gAuthUserInfoDto: GAuthUserInfoDto): Account {
        val account = Account(
            idx = UUID.randomUUID(),
            email = gAuthUserInfoDto.email,
            name = gAuthUserInfoDto.name,
            studentNumber = StudentNumber(
                grade = gAuthUserInfoDto.grade,
                classNum = gAuthUserInfoDto.classNum,
                number = gAuthUserInfoDto.num
            ),
            profileUrl = gAuthUserInfoDto.profileUrl,
            authority = Authority.ROLE_STUDENT,
            createdTime = LocalDateTime.now()
        )
        accountRepository.save(account)
        return account
    }

}