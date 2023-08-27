package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.data.dto.GAuthTokenDto
import com.goms.v2.domain.auth.data.dto.GAuthUserInfoDto
import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.data.event.SaveRefreshTokenEvent
import com.goms.v2.domain.auth.spi.OAuthPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.auth.usecase.SignInUseCase
import com.goms.v2.repository.account.AccountRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.context.ApplicationEventPublisher
import java.util.*

class SignInUseCaseTest: BehaviorSpec({
    val accountRepository = mockk<AccountRepository>()
    val oAuthPort = mockk<OAuthPort>()
    val tokenPort = mockk<TokenPort>()
    val publisher = mockk<ApplicationEventPublisher>()
    val signInUseCase = SignInUseCase(accountRepository, oAuthPort, tokenPort, publisher)

    Given("SignInDto 가 주어졌을때") {
        val code = "test GAuthCode"
        val uuid = UUID.randomUUID()
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val email = "test@test.com"
        val signInDto = AnyValueObjectGenerator.anyValueObject<SignInDto>("code" to code)
        val gAuthToken = AnyValueObjectGenerator.anyValueObject<GAuthTokenDto>("accessToken" to accessToken, "refreshToken" to refreshToken)
        val gAuthInfo = AnyValueObjectGenerator.anyValueObject<GAuthUserInfoDto>("email" to email)
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to uuid, "email" to email, "authority" to Authority.ROLE_STUDENT)
        val tokenDto = AnyValueObjectGenerator.anyValueObject<TokenDto>("accessToken" to accessToken)

        every { oAuthPort.receiveGAuthToken(signInDto.code) } returns gAuthToken
        every { oAuthPort.receiveUserInfo(gAuthToken.accessToken) } returns gAuthInfo
        every { accountRepository.findByEmail(gAuthInfo.email) } returns null
        every { accountRepository.save(any()) } returns account
        every { tokenPort.generateToken(account.idx,account.authority) } returns tokenDto
        every { publisher.publishEvent(ofType(SaveRefreshTokenEvent::class)) } just Runs

        When("첫 로그인 시도를 요청하면") {
            val result = signInUseCase.execute(signInDto)

            Then("RefreshToken이 저장되어야 한다.") {
                verify(exactly = 1) { publisher.publishEvent(ofType(SaveRefreshTokenEvent::class)) }
            }

            Then("result와 tokenDto는 같아야 한다.") {
                result shouldBe tokenDto
            }
        }

        When("로그인 시도 했었던 계정이 요청하면") {
            every { accountRepository.findByEmail(gAuthInfo.email) } returns account
            val result = signInUseCase.execute(signInDto)

            Then("계정이 저장되지 않아야 한다.") {
                verify { accountRepository.save(account) wasNot called }
            }

            Then("result와 tokenDto는 같아야 한다.") {
                result shouldBe tokenDto
            }
        }
    }
})