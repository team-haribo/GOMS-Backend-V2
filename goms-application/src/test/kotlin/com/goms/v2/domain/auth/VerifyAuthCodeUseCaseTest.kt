package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.auth.data.event.CreateAuthenticationEvent
import com.goms.v2.domain.auth.exception.AuthCodeNotFoundException
import com.goms.v2.domain.auth.exception.AuthCodeNotMatchException
import com.goms.v2.domain.auth.exception.AuthenticationNotFoundException
import com.goms.v2.domain.auth.exception.TooManyAuthCodeRequestException
import com.goms.v2.domain.auth.usecase.VerifyAuthCodeUseCase
import com.goms.v2.repository.auth.AuthCodeRepository
import com.goms.v2.repository.auth.AuthenticationRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class VerifyAuthCodeUseCaseTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val authCodeRepository = mockk<AuthCodeRepository>()
    val authenticationRepository = mockk<AuthenticationRepository>()
    val publisher = mockk<ApplicationEventPublisher>()
    val verifyAuthCodeUseCase = VerifyAuthCodeUseCase(authCodeRepository, authenticationRepository, publisher)

    Given("email과 authCode가 주어지면") {
        val email = ""
        val authCode = ""
        val notMatchAuthCode = "123"
        val authCodeDomain = AnyValueObjectGenerator.anyValueObject<AuthCode>("email" to email)
        val authentication = AnyValueObjectGenerator.anyValueObject<Authentication>("email" to email)
        val authenticationCertified = AnyValueObjectGenerator.anyValueObject<Authentication>("isAuthentication" to true)
        val increaseCountAuthentication = AnyValueObjectGenerator.anyValueObject<Authentication>("authCodeCount" to 1)
        val createAuthenticationEvent = AnyValueObjectGenerator.anyValueObject<CreateAuthenticationEvent>("authentication" to authenticationCertified)
        val increaseAuthCodeCountEvent = AnyValueObjectGenerator.anyValueObject<CreateAuthenticationEvent>("authentication" to increaseCountAuthentication)
        val tooManyAuthCode = AnyValueObjectGenerator.anyValueObject<Authentication>("authCodeCount" to 6)

        every { authCodeRepository.findByIdOrNull(email) } returns authCodeDomain
        every { authenticationRepository.findByIdOrNull(email) } returns authentication
        every { publisher.publishEvent(createAuthenticationEvent) } returns Unit

        When("인증번호 검증 요청을 하면") {
            verifyAuthCodeUseCase.execute(email, authCode)

            Then("인증이 완료되는 이벤트가 발생해야 한다.") {
                verify(exactly = 1) { publisher.publishEvent(createAuthenticationEvent) }
            }
        }

        When("authCode가 존재하지 않는다면") {
            every { authCodeRepository.findByIdOrNull(email) } returns null

            Then("AuthCodeNotFoundException이 터져야 한다.") {
                shouldThrow<AuthCodeNotFoundException> {
                    verifyAuthCodeUseCase.execute(email, authCode)
                }
            }
        }

        When("Authentication이 존재하지 않는다면") {
            every { authenticationRepository.findByIdOrNull(email) } returns null

            Then("AuthenticationNotFoundException이 터져야 한다.") {
                shouldThrow<AuthenticationNotFoundException> {
                    verifyAuthCodeUseCase.execute(email, authCode)
                }
            }
        }

        When("인증번호 검증 요청이 5번 이상이라면") {
            every { authenticationRepository.findByIdOrNull(email) } returns tooManyAuthCode

            Then("TooManyAuthCodeRequestException이 터져야 한다.") {
                shouldThrow<TooManyAuthCodeRequestException> {
                    verifyAuthCodeUseCase.execute(email, authCode)
                }
            }
        }

        When("인증번호가 일치 하지 않는다면") {
            every { publisher.publishEvent(increaseAuthCodeCountEvent) } returns Unit

            Then("AuthCodeNotMatchException이 터져야 하고 인증번호 검증 횟수 증가 이벤트가 발생해야 한다.") {
                shouldThrow<AuthCodeNotMatchException> {
                    verifyAuthCodeUseCase.execute(email, notMatchAuthCode)
                }
                verify(exactly = 1) { publisher.publishEvent(increaseAuthCodeCountEvent) }
            }
        }
    }
})
