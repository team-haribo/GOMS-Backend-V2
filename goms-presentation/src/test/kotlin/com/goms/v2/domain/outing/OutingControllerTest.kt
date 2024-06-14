package com.goms.v2.domain.outing

import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.domain.outing.dto.response.OutingAccountHttpResponse
import com.goms.v2.domain.outing.dto.response.OutingCountHttpResponse
import com.goms.v2.domain.outing.mapper.OutingDataMapper
import com.goms.v2.domain.outing.usecase.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalTime
import java.util.*

class OutingControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val outingDataMapper = mockk<OutingDataMapper>()
    val outingUseCase = mockk<OutingUseCase>()
    val queryOutingAccountUseCase = mockk<QueryOutingAccountUseCase>()
    val queryOutingCountUseCase = mockk<QueryOutingCountUseCase>()
    val searchOutingAccountUseCase = mockk<SearchOutingAccountUseCase>()
    val validateOutingTimeUseCase = mockk<ValidateOutingTimeUseCase>()
    val outingController = OutingController(
        outingDataMapper,
        outingUseCase,
        queryOutingAccountUseCase,
        queryOutingCountUseCase,
        searchOutingAccountUseCase,
        validateOutingTimeUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(outingController).build()
    }

    describe("api/v2/outing/{outingUUID}로 post 요청을 했을때") {
        val url = "/api/v2/outing/{outingUUID}"
        val outingUUID = UUID.randomUUID()

        every { outingUseCase.execute(outingUUID) } returns Unit

        context("유효한 요청이 전달 되면") {
            mockMvc.perform(
                post(url, outingUUID)
            )
                .andExpect(status().`is`(204))
        }
    }

    describe("api/v2/outing로 get 요청을 했을때") {
        val url = "/api/v2/outing"
        val accountUUID = UUID.randomUUID()

        context("유효한 요청이 전달 되면") {
            val outingAccountDto = OutingAccountDto(
                accountIdx = accountUUID,
                name = "김경수",
                grade = 6,
                major = Major.SMART_IOT,
                gender = Gender.MAN,
                profileUrl = null,
                createdTime = LocalTime.now()
            )
            val outingAccountHttpResponse = OutingAccountHttpResponse(
                accountIdx = accountUUID,
                name = "김경수",
                grade = 6,
                major = Major.SMART_IOT,
                gender = Gender.MAN,
                profileUrl = null,
                createdTime = LocalTime.now()
            )

            every { queryOutingAccountUseCase.execute() } returns listOf(outingAccountDto)
            every { outingDataMapper.toResponse(outingAccountDto) } returns outingAccountHttpResponse

            it("outingAccountHttpResponse를 반환한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpect(status().`is`(200))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].accountIdx").value(outingAccountHttpResponse.accountIdx.toString()))
                    .andExpect(jsonPath("$[0].name").value(outingAccountHttpResponse.name))
                    .andExpect(jsonPath("$[0].grade").value(outingAccountHttpResponse.grade))
                    .andExpect(jsonPath("$[0].gender").value(outingAccountHttpResponse.gender))
                    .andExpect(jsonPath("$[0].profileUrl").value(outingAccountHttpResponse.profileUrl))
                    .andExpect(jsonPath("$[0].createdTime").value(outingAccountHttpResponse.createdTime))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("api/v2/outing/count로 get 요청 했을때") {
        val url = "/api/v2/outing/count"
        val count = 0L

        context("유효한 요청이 전달 되면") {
            val outingCountHttpResponse = OutingCountHttpResponse(outingCount = count)

            every { queryOutingCountUseCase.execute() } returns count
            every { outingDataMapper.toResponse(count) } returns outingCountHttpResponse

            it("OutingCountHttpResponse를 반환한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpect(status().`is`(200))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.outingCount").value(outingCountHttpResponse.outingCount))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("api/v2/outing/search로 get 요청 했을때") {
        val url = "/api/v2/outing/search"
        val accountUUID = UUID.randomUUID()

        context("유효한 요청이 전달 되면") {
            val outingAccountDto = OutingAccountDto(
                accountIdx = accountUUID,
                name = "김경수",
                grade = 6,
                major = Major.SMART_IOT,
                gender = Gender.MAN,
                profileUrl = null,
                createdTime = LocalTime.now()
            )
            val outingAccountHttpResponse = OutingAccountHttpResponse(
                accountIdx = accountUUID,
                name = "김경수",
                grade = 6,
                major = Major.SMART_IOT,
                gender = Gender.MAN,
                profileUrl = null,
                createdTime = LocalTime.now()
            )

            every { searchOutingAccountUseCase.execute(null) } returns listOf(outingAccountDto)
            every { outingDataMapper.toResponse(outingAccountDto) } returns outingAccountHttpResponse

            it("OutingAccountHttpResponse를 반환한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpect(status().`is`(200))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].accountIdx").value(outingAccountHttpResponse.accountIdx.toString()))
                    .andExpect(jsonPath("$[0].name").value(outingAccountHttpResponse.name))
                    .andExpect(jsonPath("$[0].grade").value(outingAccountHttpResponse.grade))
                    .andExpect(jsonPath("$[0].gender").value(outingAccountHttpResponse.gender))
                    .andExpect(jsonPath("$[0].profileUrl").value(outingAccountHttpResponse.profileUrl))
                    .andExpect(jsonPath("$[0].createdTime").value(outingAccountHttpResponse.createdTime))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("api/v2/outing/validation을 get으로 요청 했을때") {
        val url = "/api/v2/outing/validation"

        context("유효한 요청이 전달 되면") {

            every { validateOutingTimeUseCase.execute() } returns true

            it("isOuting to value가 반환한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpect(status().`is`(200))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.isOuting").value(true))
            }
        }
    }
})