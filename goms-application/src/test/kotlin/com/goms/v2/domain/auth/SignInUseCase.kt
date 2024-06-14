package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.data.event.SaveRefreshTokenEvent
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.domain.auth.spi.TokenPort
import com.goms.v2.domain.auth.usecase.SignInUseCase
import com.goms.v2.repository.account.AccountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.context.ApplicationEventPublisher
import java.util.*

class SignInUseCaseTest: BehaviorSpec({

    val accountRepository = mockk<AccountRepository>()
    val tokenPort = mockk<TokenPort>()
    val passwordEncoderPort = mockk<PasswordEncoderPort>()
    val publisher = mockk<ApplicationEventPublisher>()
    val signInUseCase = SignInUseCase(accountRepository, tokenPort, passwordEncoderPort, publisher)

    Given("SignInDto가 주어지면") {
        val accountIdx = UUID.randomUUID()
        val email = "s22039@gsm.hs.kr"
        val accessToken = "accessToken"
        val signInDto = AnyValueObjectGenerator.anyValueObject<SignInDto>("email" to email)
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)
        val tokenDto = AnyValueObjectGenerator.anyValueObject<TokenDto>("accessToken" to accessToken)

        every { accountRepository.findByEmail(signInDto.email) } returns account
        every { passwordEncoderPort.isPasswordMatch(signInDto.password, account.password) } returns true
        every { tokenPort.generateToken(account.idx, account.authority) } returns tokenDto
        every { publisher.publishEvent(ofType(SaveRefreshTokenEvent::class)) } just Runs

        When("로그인 요청을 하면"){
            val result = signInUseCase.execute(signInDto)

            Then("RefreshToken이 저장되어야 한다.") {
                verify(exactly = 1) { publisher.publishEvent(ofType(SaveRefreshTokenEvent::class)) }
            }
            Then("result와 tokenDto가 같아야 한다.") {
                result shouldBe tokenDto
            }
        }
        When("존재하지 않는 id 이면") {
            every { accountRepository.findByEmail(signInDto.email) } returns null

            Then("AccountNotFoundException 이 터져야 합니다.") {
                shouldThrow<AccountNotFoundException> {
                    signInUseCase.execute(signInDto)
                }
            }
        }
        When("비밀번호가 일치하지 않으면") {
            every { accountRepository.findByEmail(signInDto.email) } returns account
            every { passwordEncoderPort.isPasswordMatch(signInDto.password, account.password) } returns false

            Then("PasswordNotMatchException 이 터져야 합니다.") {
                shouldThrow<PasswordNotMatchException> {
                    signInUseCase.execute(signInDto)
                }
            }
        }
    }
})
