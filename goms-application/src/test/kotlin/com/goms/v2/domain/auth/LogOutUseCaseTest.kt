package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.auth.exception.ExpiredRefreshTokenException
import com.goms.v2.domain.auth.exception.InvalidTokenTypeException
import com.goms.v2.domain.auth.spi.TokenParsePort
import com.goms.v2.domain.auth.usecase.LogoutUseCase
import com.goms.v2.repository.auth.RefreshTokenRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class LogOutUseCaseTest: BehaviorSpec({
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val tokenParsePort = mockk<TokenParsePort>()
    val logOutUseCase = LogoutUseCase(refreshTokenRepository, tokenParsePort)

    Given("refreshToken 이 주여지면") {
        val refreshToken = "refreshToken"
        val refreshTokenDomain = AnyValueObjectGenerator.anyValueObject<RefreshToken>("refreshToken" to refreshToken)

        every { tokenParsePort.parseRefreshToken(refreshToken) } returns refreshToken
        every { refreshTokenRepository.findByIdOrNull(refreshToken) } returns refreshTokenDomain
        every { refreshTokenRepository.deleteById(refreshTokenDomain.refreshToken) } returns Unit

        When("로그아웃 요청을 하면") {
            logOutUseCase.execute(refreshToken)

            Then("Account 가 저장이 되어야 한다.") {
                verify(exactly = 1) { refreshTokenRepository.deleteById(any()) }
            }
        }
        When("유효하지 않는 토큰 타입으로 요청하면") {
            every { tokenParsePort.parseRefreshToken(refreshToken) } returns null

            Then("InvalidTokenTypeException이 터저야 한다.") {
                shouldThrow<InvalidTokenTypeException> {
                    logOutUseCase.execute(refreshToken)
                }
            }
        }
        When("만료된 토큰으로 요청하면") {
            every { tokenParsePort.parseRefreshToken(refreshToken) } returns refreshToken
            every { refreshTokenRepository.findByIdOrNull(refreshToken) } returns null

            Then("ExpiredRefreshTokenException이 터져야 한다.") {
                shouldThrow<ExpiredRefreshTokenException> {
                    logOutUseCase.execute(refreshToken)
                }
            }
        }
    }
})