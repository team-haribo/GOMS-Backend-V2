package com.goms.v2.domain.email

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthDataMapper
import com.goms.v2.domain.email.data.dto.EmailDto
import com.goms.v2.domain.email.dto.request.SendEmailHttpRequest
import com.goms.v2.domain.email.mapper.EmailAuthDataMapper
import com.goms.v2.domain.email.usecase.SendEmailUseCase
import com.goms.v2.domain.email.usecase.VerifyAuthCodeUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class EmailAuthControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val emailAuthDataMapper = mockk<EmailAuthDataMapper>()
    val authDataMapper = mockk<AuthDataMapper>()
    val sendEmailUseCase = mockk<SendEmailUseCase>()
    val verifyAuthCodeUseCase = mockk<VerifyAuthCodeUseCase>()
    val emailAuthController = EmailAuthController(
        emailAuthDataMapper,
        authDataMapper,
        sendEmailUseCase,
        verifyAuthCodeUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(emailAuthController).build()
    }

    describe("api/v2/email/send 로 post 요청을 했을때") {
        val url = "/api/v2/email/send"
        val email = "test@test.com"

        context("유효한 요청이 전달 되면") {
            val sendEmailHttpRequest = SendEmailHttpRequest(email)
            val emailDto = EmailDto(email)

            every { emailAuthDataMapper.toDto(sendEmailHttpRequest) } returns emailDto
            every { sendEmailUseCase.execute(emailDto) } returns Unit

            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(sendEmailHttpRequest)

            it("이메일을 전송한다.") {

                mockMvc.perform(
                    post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(204))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("api/v2/email/verify 로 get 요청을 했을때") {
        val url = "/api/v2/email/verify"

        context("유효한 요청이 전달 되면") {
            val email = "test@test.com"
            val authCode = "1234"
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

            every { verifyAuthCodeUseCase.execute(email, authCode) } returns tokenDto
            every { authDataMapper.toResponse(tokenDto) } returns tokenHttpResponse

            it("TokenHttpResponse를 반환한다.") {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                val requestParam = LinkedMultiValueMap<String, String>()

                requestParam.add("email", email)
                requestParam.add("authCode", authCode)

                mockMvc.perform(
                    get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(requestParam)
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
})