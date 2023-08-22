package com.goms.v2.domain.email

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
})