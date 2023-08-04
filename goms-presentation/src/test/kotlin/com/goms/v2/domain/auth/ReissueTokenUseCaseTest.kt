package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.ExpiredRefreshTokenException
import com.goms.v2.domain.auth.exception.InvalidTokenTypeException
import com.goms.v2.domain.auth.spi.TokenParserPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.auth.usecase.ReissueTokenUseCase
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.auth.RefreshTokenRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ReissueTokenUseCaseTest: BehaviorSpec({
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val accountRepository = mockk<AccountRepository>()
    val tokenPort = mockk<TokenPort>()
    val tokenParserPort = mockk<TokenParserPort>()
    val reissueTokenUseCase = ReissueTokenUseCase(refreshTokenRepository, accountRepository, tokenPort, tokenParserPort)

    Given("refreshToken이 주어졌을때") {
        val refreshToken = "Bearer test refreshToken"
        val parsedRefreshToken = "test refreshToken"
        val tokenDto = AnyValueObjectGenerator.anyValueObject<TokenDto>("refreshToken" to parsedRefreshToken)
        val refreshTokenDomain = AnyValueObjectGenerator.anyValueObject<RefreshToken>("refreshToken" to parsedRefreshToken)
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to refreshTokenDomain.accountIdx)

        every { tokenParserPort.parseRefreshToken(refreshToken) } returns parsedRefreshToken
        every { refreshTokenRepository.findByIdOrNull(parsedRefreshToken) } returns refreshTokenDomain
        every { accountRepository.findByIdOrNull(refreshTokenDomain.accountIdx) } returns account
        every { tokenPort.generateToken(refreshTokenDomain.accountIdx, account.authority) } returns tokenDto
        every { refreshTokenRepository.save(refreshTokenDomain) } returns Unit
        every { refreshTokenRepository.deleteById(parsedRefreshToken) } returns Unit

        When("토큰 재발급을 요청하면") {
            val result = reissueTokenUseCase.execute(refreshToken)

            Then("토큰이 재발급 되어야 한다.") {
                verify(exactly = 1) { tokenPort.generateToken(account.idx, account.authority) }
            }

            Then("기존의 토큰이 삭제 되어야 한다.") {
                verify(exactly = 1) { refreshTokenRepository.deleteById(parsedRefreshToken)}
            }

            Then("result와 tokenDto는 같아야한다.") {
                result shouldBe tokenDto
            }
        }

        When("유효하지 않는 토큰 타입으로 요청하면") {
            every { tokenParserPort.parseRefreshToken(refreshToken) } returns null

            Then("InvalidTokenTypeException이 터저야 한다.") {
                shouldThrow<InvalidTokenTypeException> {
                    reissueTokenUseCase.execute(refreshToken)
                }
            }
        }

        When("만료된 토큰으로 요청하면") {
            every { tokenParserPort.parseRefreshToken(refreshToken) } returns parsedRefreshToken
            every { refreshTokenRepository.findByIdOrNull(parsedRefreshToken) } returns null

            Then("ExpiredRefreshTokenException이 터져야 한다.") {
                shouldThrow<ExpiredRefreshTokenException> {
                    reissueTokenUseCase.execute(refreshToken)
                }
            }
        }

        When("계정을 찾을 수 없으면") {
            every { tokenParserPort.parseRefreshToken(refreshToken) } returns parsedRefreshToken
            every { refreshTokenRepository.findByIdOrNull(parsedRefreshToken) } returns refreshTokenDomain
            every { accountRepository.findByIdOrNull(refreshTokenDomain.accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    reissueTokenUseCase.execute(refreshToken)
                }
            }
        }
    }
})