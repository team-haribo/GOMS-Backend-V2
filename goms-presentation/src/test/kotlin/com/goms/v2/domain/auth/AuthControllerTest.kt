package com.goms.v2.domain.auth

import com.goms.v2.common.AnyValueObjectGenerator
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthDataMapper
import com.goms.v2.domain.auth.usecase.ReissueTokenUseCase
import com.goms.v2.domain.auth.usecase.SignInUseCase
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@SpringBootTest(classes = [AuthControllerTest::class])
@AutoConfigureMockMvc
class AuthControllerTest {

    private lateinit var mockMvc: MockMvc
    val authDataMapper = mock<AuthDataMapper>()
    val signInUseCase = mock<SignInUseCase>()
    val reissueTokenUseCase = mock<ReissueTokenUseCase>()
    val authController = AuthController(authDataMapper, signInUseCase, reissueTokenUseCase)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build()
    }

    @Test
    fun `로그인 성공`() {
        val tokenHttpResponse = AnyValueObjectGenerator.anyValueObject<TokenHttpResponse>(
            "accessToken" to "accessToken",
            "refreshToken" to "refreshToken",
            "accessTokenExp" to LocalDateTime.now(),
            "refreshTokenExp" to LocalDateTime.now(),
            "authority" to Authority.ROLE_STUDENT
        )

        val signInJson = """
            {
                "code": "test GAuthCode"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/v2/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(signInJson)
        )
            .andExpect{ status().isOk }
            .andExpect{content().contentType(MediaType.APPLICATION_JSON_VALUE)}
            .andExpect{jsonPath("$.accessToken").value(tokenHttpResponse.accessToken)}
            .andExpect{jsonPath("$.refreshToken").value(tokenHttpResponse.refreshToken)}
            .andExpect{jsonPath("$.accessTokenExp").value(tokenHttpResponse.accessTokenExp)}
            .andExpect{jsonPath("$.refreshTokenExp").value(tokenHttpResponse.refreshTokenExp)}
            .andExpect{jsonPath("$.authority").value(tokenHttpResponse.authority)}
            .andDo{MockMvcResultHandlers.print()}

    }

    @Test
    fun `토큰 재발급 성공`() {
        val refreshToken = "testRefreshToken"
        val tokenHttpResponse = AnyValueObjectGenerator.anyValueObject<TokenHttpResponse>(
            "accessToken" to "accessToken",
            "refreshToken" to "refreshToken",
            "accessTokenExp" to LocalDateTime.now(),
            "refreshTokenExp" to LocalDateTime.now(),
            "authority" to Authority.ROLE_STUDENT
        )

        mockMvc.perform(
            patch("/api/v2/auth")
                .header("refreshToken", refreshToken)
        )
            .andExpect{ status().isOk }
            .andExpect{content().contentType(MediaType.APPLICATION_JSON_VALUE)}
            .andExpect{jsonPath("$.accessToken").value(tokenHttpResponse.accessToken)}
            .andExpect{jsonPath("$.refreshToken").value(tokenHttpResponse.refreshToken)}
            .andExpect{jsonPath("$.accessTokenExp").value(tokenHttpResponse.accessTokenExp)}
            .andExpect{jsonPath("$.refreshTokenExp").value(tokenHttpResponse.refreshTokenExp)}
            .andExpect{jsonPath("$.authority").value(tokenHttpResponse.authority)}
            .andDo{MockMvcResultHandlers.print()}
    }

}