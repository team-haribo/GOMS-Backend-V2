package com.goms.v2.domain.account

import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.data.dto.StudentNumberDto
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import com.goms.v2.domain.account.dto.response.StudentNumHttpResponse
import com.goms.v2.domain.account.mapper.AccountDataMapper
import com.goms.v2.domain.account.usecase.QueryAccountProfileUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class AccountControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val accountDataMapper = mockk<AccountDataMapper>()
    val queryAccountProfileUseCase = mockk<QueryAccountProfileUseCase>()
    val accountController = AccountController(
        accountDataMapper,
        queryAccountProfileUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build()
    }

    describe("api/v2/account/profile로 get 요청을 했을때") {
        val url = "/api/v2/account/profile"
        val profileDto = ProfileDto(
            name = "김경수",
            studentNum = StudentNumberDto(
                grade = 2,
                classNum = 4,
                number = 2
            ),
            authority = Authority.ROLE_STUDENT,
            profileUrl = null,
            lateCount = 0,
            isOuting = false,
            isBlackList = false
        )
        val profileHttpResponse = ProfileHttpResponse(
            name = "김경수",
            studentNum = StudentNumHttpResponse(
                grade = 2,
                classNum = 4,
                number = 2
            ),
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
                    .andExpect(jsonPath("$.studentNum.grade").value(profileHttpResponse.studentNum.grade))
                    .andExpect(jsonPath("$.studentNum.classNum").value(profileHttpResponse.studentNum.classNum))
                    .andExpect(jsonPath("$.studentNum.number").value(profileHttpResponse.studentNum.number))
                    .andExpect(jsonPath("$.authority").value(profileHttpResponse.authority.toString()))
                    .andExpect(jsonPath("$.profileUrl").value(profileHttpResponse.profileUrl))
                    .andExpect(jsonPath("$.lateCount").value(profileHttpResponse.lateCount))
                    .andExpect(jsonPath("$.isOuting").value(profileHttpResponse.isOuting))
                    .andExpect(jsonPath("$.isBlackList").value(profileHttpResponse.isBlackList))
                    .andDo(MockMvcResultHandlers.print())
            }
        }

    }

})