package com.goms.v2.domain.account

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.account.data.dto.PasswordDto
import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.dto.request.UpdatePasswordRequest
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import com.goms.v2.domain.account.mapper.AccountDataMapper
import com.goms.v2.domain.account.usecase.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream
import java.nio.charset.StandardCharsets

class AccountControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val accountDataMapper = mockk<AccountDataMapper>()
    val queryAccountProfileUseCase = mockk<QueryAccountProfileUseCase>()
    val updatePasswordUseCase = mockk<UpdatePasswordUseCase>()
    val uploadImageUseCase = mockk<UploadImageUseCase>()
    val updateImageUseCase = mockk<UpdateImageUseCase>()
    val deleteImageUseCase = mockk<DeleteImageUseCase>()
    val accountController = AccountController(
        accountDataMapper,
        queryAccountProfileUseCase,
        updatePasswordUseCase,
        uploadImageUseCase,
        updateImageUseCase,
        deleteImageUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build()
    }

    describe("api/v2/account/profile로 get 요청을 했을때") {
        val url = "/api/v2/account/profile"
        val profileDto = ProfileDto(
            name = "김경수",
            grade = 6,
            gender = Gender.MAN,
            major = Major.SMART_IOT,
            authority = Authority.ROLE_STUDENT,
            profileUrl = null,
            lateCount = 0,
            isOuting = false,
            isBlackList = false
        )
        val profileHttpResponse = ProfileHttpResponse(
            name = "김경수",
            grade = 6,
            gender = Gender.MAN,
            major = Major.SMART_IOT,
            authority = Authority.ROLE_STUDENT,
            profileUrl = null,
            lateCount = 0,
            isOuting = false,
            isBlackList = false
        )

        context("유효한 요청이 전달 되면") {
            every { queryAccountProfileUseCase.execute() } returns profileDto
            every { accountDataMapper.toResponse(profileDto) } returns profileHttpResponse

            it("profileHttpResponse를 반환한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpect(status().`is`(200))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value(profileHttpResponse.name))
                    .andExpect(jsonPath("$.grade").value(profileHttpResponse.grade))
                    .andExpect(jsonPath("$.gender").value(profileHttpResponse.gender.toString()))
                    .andExpect(jsonPath("$.authority").value(profileHttpResponse.authority.toString()))
                    .andExpect(jsonPath("$.profileUrl").value(profileHttpResponse.profileUrl))
                    .andExpect(jsonPath("$.lateCount").value(profileHttpResponse.lateCount))
                    .andExpect(jsonPath("$.isOuting").value(profileHttpResponse.isOuting))
                    .andExpect(jsonPath("$.isBlackList").value(profileHttpResponse.isBlackList))
                    .andDo(MockMvcResultHandlers.print())
            }
        }

    }

    describe("api/v2/account/new-password patch 요청을 했을때") {
        val url = "/api/v2/account/new-password"

        val updatePasswordRequest = UpdatePasswordRequest(
            email = "s22039@gsm.hs.kr",
            newPassword = "gomstest1234!"
        )
        val passwordDto = PasswordDto(
            email = "s22039@gsm.hs.kr",
            newPassword = "gomstest1234!"
        )

        context("유효한 요청이 전달 되면") {

            every { accountDataMapper.toDomain(updatePasswordRequest) } returns passwordDto
            every { updatePasswordUseCase.execute(passwordDto) } returns Unit
            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(updatePasswordRequest)

            it("204 status code를 응답해야한다.") {
                mockMvc.perform(
                    patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(204))
            }
        }
    }

    describe("api/v2/account/image로 post 요청을 했을때") {
        val url = "/api/v2/account/image"

        val imageBytes = "image content".toByteArray(StandardCharsets.UTF_8)
        val image = MockMultipartFile("File", "image.png", "image/png", imageBytes)

        context("유효한 요청이 전달 되면") {

            every { uploadImageUseCase.execute(any()) } returns Unit

            it("205 status code를 응답해야한다.") {
                mockMvc.perform(
                    multipart(url)
                        .file(image)
                )
                    .andExpect(status().`is`(205))
            }
        }
    }
})