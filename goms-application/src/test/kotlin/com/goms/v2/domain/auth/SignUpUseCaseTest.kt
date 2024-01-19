package com.goms.v2.domain.auth

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.exception.AlreadyExistEmailException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.domain.auth.usecase.SignUpUseCase
import com.goms.v2.repository.account.AccountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*

class SignUpUseCaseTest: BehaviorSpec({
    val accountRepository = mockk<AccountRepository>()
    val passwordEncoderPort = mockk<PasswordEncoderPort>()
    val signUpUseCase = SignUpUseCase(accountRepository, passwordEncoderPort)

    Given("SignUpDto 가 주어질때") {
        val encodePassword = "encodePassword"
        val signUpDto = SignUpDto(
            email = "s22039@gsm.hs.kr",
            password = "gomstest1234!",
            name = "김경수",
            gender = Gender.MAN,
            major = Major.SMART_IOT
        )
        val account = Account(
            idx = UUID.randomUUID(),
            email = "s22039@gsm.hs.kr",
            password = "gomstest1234!",
            name = "김경수",
            grade = 6,
            gender = Gender.MAN,
            major = Major.SMART_IOT,
            profileUrl = null,
            authority = Authority.ROLE_STUDENT,
            createdTime = LocalDateTime.now()
        )

        every { accountRepository.existsByEmail(signUpDto.email) } returns false
        every { passwordEncoderPort.passwordEncode(signUpDto.password) } returns encodePassword
        every { accountRepository.save(any()) } returns account

        When("회원가입 요청을 하면") {
            signUpUseCase.execute(signUpDto)

            Then("Account 가 저장이 되어야 한다.") {
                verify(exactly = 1) { accountRepository.save(any()) }
            }
        }

        When("이미 존재하는 email로 요청을 하면") {
            every { accountRepository.existsByEmail(signUpDto.email) } returns true

            Then("AlreadyExistEmailException이 터져야 한다.") {
                shouldThrow<AlreadyExistEmailException> {
                    signUpUseCase.execute(signUpDto)
                }
            }
        }
    }
})