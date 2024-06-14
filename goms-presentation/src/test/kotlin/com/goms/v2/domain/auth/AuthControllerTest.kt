package com.goms.v2.domain.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.auth.data.dto.SendAuthCodeDto
import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.dto.request.SendAuthCodeHttpRequest
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.request.SignUpHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthCodeDataMapper
import com.goms.v2.domain.auth.mapper.AuthDataMapper
import com.goms.v2.domain.auth.usecase.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AuthControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val authDataMapper = mockk<AuthDataMapper>()
    val authCodeDataMapper = mockk<AuthCodeDataMapper>()
    val signUpUseCase = mockk<SignUpUseCase>()
    val signInUseCase = mockk<SignInUseCase>()
    val reissueTokenUseCase = mockk<ReissueTokenUseCase>()
    val sendAuthCodeUseCase = mockk<SendAuthCodeUseCase>()
    val verifyAuthCodeUseCase = mockk<VerifyAuthCodeUseCase>()
    val logoutUseCase = mockk<LogoutUseCase>()
    val authController = AuthController(
        authDataMapper,
        authCodeDataMapper,
        signUpUseCase,
        signInUseCase,
        reissueTokenUseCase,
        sendAuthCodeUseCase,
        verifyAuthCodeUseCase,
        logoutUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build()
    }

    describe("/api/v2/auth/signup 으로 post 요청을 했을때") {
        val url = "/api/v2/auth/signup"

        context("회원가입 요청이 전달 되면") {
            val signUpHttpRequest = SignUpHttpRequest(
                email = "s22039@gsm.hs.kr",
                password = "gomstest1234!",
                name = "김경수",
                major = Major.SMART_IOT,
                gender = Gender.MAN
            )
            val signUpDto = SignUpDto(
                email = "s22039@gsm.hs.kr",
                password = "gomstest1234!",
                name = "김경수",
                major = Major.SMART_IOT,
                gender = Gender.MAN
            )

            every { authDataMapper.toDto(signUpHttpRequest) } returns signUpDto
            every { signUpUseCase.execute(signUpDto) } returns Unit

            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(signUpHttpRequest)

            it("201 상태코드를 반환한다.") {

                mockMvc.perform(
                    post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(201))
            }
        }
    }

    describe("/api/v2/auth/signin 으로 post 요청을 했을때") {
        val url = "/api/v2/auth/signin"

        context("로그인 요청이 전달 되면") {
            val signInHttpRequest = SignInHttpRequest(
                email = "s22039@gsm.hs.kr",
                password = "gomstest1234!"
            )
            val signInDto = SignInDto(
                email = "s22039@gsm.hs.kr",
                password = "gomstest1234!"
            )
            val tokenDto = TokenDto(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
                accessTokenExp = 0,
                refreshTokenExp = 0,
                authority = Authority.ROLE_STUDENT
            )
            val tokenHttpResponse = TokenHttpResponse(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
                accessTokenExp = LocalDateTime.parse("2023-08-20T23:23:14"),
                refreshTokenExp = LocalDateTime.parse("2023-08-20T23:23:14"),
                authority = Authority.ROLE_STUDENT
            )

            every { authDataMapper.toDto(signInHttpRequest) } returns signInDto
            every { signInUseCase.execute(signInDto) } returns tokenDto
            every { authDataMapper.toResponse(tokenDto) } returns tokenHttpResponse

            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(signInHttpRequest)

            it("TokenHttpResponse를 반환한다.") {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

                mockMvc.perform(
                    post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(200))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.accessToken").value(tokenHttpResponse.accessToken))
                    .andExpect(jsonPath("$.refreshToken").value(tokenHttpResponse.refreshToken))
                    .andExpect(jsonPath("$.accessTokenExp").value(formatter.format(tokenHttpResponse.accessTokenExp)))
                    .andExpect(jsonPath("$.refreshTokenExp").value(formatter.format(tokenHttpResponse.refreshTokenExp)))
                    .andExpect(jsonPath("$.authority").value(tokenHttpResponse.authority.toString()))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("/api/v2/auth 으로 patch 요청을 했을때") {
        val url = "/api/v2/auth"

        context("유효한 요청이 전달 되면") {
            val tokenDto = TokenDto(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
                accessTokenExp = 0,
                refreshTokenExp = 0,
                authority = Authority.ROLE_STUDENT
            )
            val tokenHttpResponse = TokenHttpResponse(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
                accessTokenExp = LocalDateTime.parse("2023-08-20T23:23:14"),
                refreshTokenExp = LocalDateTime.parse("2023-08-20T23:23:14"),
                authority = Authority.ROLE_STUDENT
            )

            every { reissueTokenUseCase.execute("refreshToken") } returns tokenDto
            every { authDataMapper.toResponse(tokenDto) } returns tokenHttpResponse

            it("TokenHttpResponse를 반환한다.") {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

                mockMvc.perform(
                    patch(url)
                        .header("refreshToken", "refreshToken")
                )
                    .andExpect(status().`is`(200))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.accessToken").value(tokenHttpResponse.accessToken))
                    .andExpect(jsonPath("$.refreshToken").value(tokenHttpResponse.refreshToken))
                    .andExpect(jsonPath("$.accessTokenExp").value(formatter.format(tokenHttpResponse.accessTokenExp)))
                    .andExpect(jsonPath("$.refreshTokenExp").value(formatter.format(tokenHttpResponse.refreshTokenExp)))
                    .andExpect(jsonPath("$.authority").value(tokenHttpResponse.authority.toString()))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("/api/v2/auth/email/send 으로 post 요청을 했을때") {
        val url = "/api/v2/auth/email/send"

        context("유효한 요청이 전달 되면") {
            val sendAuthCodeHttpRequest = SendAuthCodeHttpRequest(
                email = "s22039@gsm.hs.kr"
            )
            val sendAuthCodeDto = SendAuthCodeDto(
                email = "s22039@gsm.hs.kr"
            )

            every { authCodeDataMapper.toDto(sendAuthCodeHttpRequest) } returns sendAuthCodeDto
            every { sendAuthCodeUseCase.execute(sendAuthCodeDto) } returns Unit

            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(sendAuthCodeHttpRequest)

            it("204 상태코드를 반환한다.") {
                mockMvc.perform(
                    post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(204))
            }
        }
    }

    describe("/api/v2/auth/email/verify 으로 get 요청을 했을때") {
        val url = "/api/v2/auth/email/verify"

        context("유효한 요청이 전달 되면") {
            val email = "s22039@gsm.hs.kr"
            val authCode = "1234"

            every { verifyAuthCodeUseCase.execute(email, authCode) } returns Unit

            it("204 상태코드를 반환한다.") {
                val requestParam = LinkedMultiValueMap<String, String>()

                requestParam.add("email", email)
                requestParam.add("authCode", authCode)

                mockMvc.perform(
                    get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(requestParam)
                )
                    .andExpect(status().`is`(204))

            }
        }
    }

    describe("/api/v2/auth 으로 delete 요청을 했을때") {
        val url = "/api/v2/auth"

        context("유효한 요청이 전달 되면") {
            val refreshToken = "refreshToken"

            every { logoutUseCase.execute(refreshToken) } returns Unit

            it("200 상태코드를 반환한다.") {
                mockMvc.perform(
                    delete(url)
                        .header("refreshToken", refreshToken)
                )
                    .andExpect(status().`is`(204))
            }
        }
    }
})