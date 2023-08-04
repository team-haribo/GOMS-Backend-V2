package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.spi.GAuthPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.StudentNumber
import com.goms.v2.domain.auth.data.dto.GAuthUserInfoDto
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
    private val gAuthPort: GAuthPort,
    private val tokenPort: TokenPort,
    private val publisher: ApplicationEventPublisher
) {

    fun execute(dto: SignInDto): TokenDto {
        val gAuthToken = gAuthPort.receiveGAuthToken(dto.code)
        log.info { "GAuth Token is ${gAuthToken.accessToken}" }
        val gAuthInfo = gAuthPort.receiveUserInfo(gAuthToken.accessToken)
        log.info { "GAuth email is ${gAuthInfo.email}" }
        val account = accountRepository.findByEmail(gAuthInfo.email) ?: saveAccount(gAuthInfo)

        val token = tokenPort.generateToken(
            account.idx,
            account.authority
        )

        publishSaveRefreshToken(token, account)

        return token
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

    private fun publishSaveRefreshToken(token: TokenDto, account: Account) {
        val saveRefreshTokenEvent = SaveRefreshTokenEvent(
            refreshToken = token.refreshToken,
            accountIdx = account.idx,
            expiredAt = token.refreshTokenExp
        )
        publisher.publishEvent(saveRefreshTokenEvent)
    }

}