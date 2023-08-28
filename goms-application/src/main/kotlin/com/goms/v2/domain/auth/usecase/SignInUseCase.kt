package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.spi.OAuthPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.StudentNumber
import com.goms.v2.domain.auth.data.dto.OAuthUserInfoDto
import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.data.event.SaveRefreshTokenEvent
import com.goms.v2.repository.account.AccountRepository
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import java.time.LocalDateTime
import java.util.*

private val log = KotlinLogging.logger { }

@UseCaseWithTransaction
class SignInUseCase(
    private val accountRepository: AccountRepository,
    private val oAuthPort: OAuthPort,
    private val tokenPort: TokenPort,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(dto: SignInDto): TokenDto {
        val gAuthToken = oAuthPort.receiveOAuthToken(dto.code)
        log.info { "GAuth Token is ${gAuthToken.accessToken}" }
        val gAuthInfo = oAuthPort.receiveUserInfo(gAuthToken.accessToken)
        log.info { "GAuth email is ${gAuthInfo.email}" }
        val account = accountRepository.findByEmail(gAuthInfo.email) ?: saveAccount(gAuthInfo)

        val token = tokenPort.generateToken(
            account.idx,
            account.authority
        )

        publishSaveRefreshToken(token, account)

        return token
    }

    private fun saveAccount(oAuthUserInfoDto: OAuthUserInfoDto): Account {
        val account = Account(
            idx = UUID.randomUUID(),
            email = oAuthUserInfoDto.email,
            name = oAuthUserInfoDto.name,
            studentNumber = StudentNumber(
                grade = oAuthUserInfoDto.grade,
                classNum = oAuthUserInfoDto.classNum,
                number = oAuthUserInfoDto.num
            ),
            profileUrl = oAuthUserInfoDto.profileUrl,
            authority = Authority.ROLE_STUDENT,
            createdTime = LocalDateTime.now()
        )
        return accountRepository.save(account)
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