package com.goms.v2.domain.late

import com.goms.v2.domain.account.data.dto.StudentNumberDto
import com.goms.v2.domain.account.dto.response.StudentNumHttpResponse
import com.goms.v2.domain.late.data.dto.LateRankDto
import com.goms.v2.domain.late.dto.LateRankHttpResponse
import com.goms.v2.domain.late.mapper.LateDataMapper
import com.goms.v2.domain.late.usecase.QueryLateRankUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.UUID

class LateControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val lateDataMapper = mockk<LateDataMapper>()
    val queryLateRankUseCase = mockk<QueryLateRankUseCase>()
    val lateController = LateController(lateDataMapper, queryLateRankUseCase)

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(lateController).build()
    }

    describe("/api/v2/late/rank 으로 GET 요청을 했을때") {
        val url = "/api/v2/late/rank"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            val lateRankDto = LateRankDto(
                accountIdx = accountIdx,
                name = "",
                studentNum = StudentNumberDto(0, 0, 0),
                profileUrl = ""
            )
            val lateRankHttpResponse = LateRankHttpResponse(
                accountIdx = accountIdx,
                name = "",
                studentNum = StudentNumHttpResponse(0, 0, 0),
                profileUrl = ""
            )
            every { queryLateRankUseCase.execute() } returns listOf(lateRankDto)
            every { lateDataMapper.toResponse(lateRankDto) } returns lateRankHttpResponse

            it("List<LateRankHttpResponse>를 응답해야한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpectAll(
                        MockMvcResultMatchers.status().isOk,
                        MockMvcResultMatchers.jsonPath("$[0].accountIdx").value(lateRankHttpResponse.accountIdx.toString()),
                        MockMvcResultMatchers.jsonPath("$[0].name").value(lateRankHttpResponse.name),
                        MockMvcResultMatchers.jsonPath("$[0].studentNum.grade").value(lateRankHttpResponse.studentNum.grade),
                        MockMvcResultMatchers.jsonPath("$[0].studentNum.classNum").value(lateRankHttpResponse.studentNum.classNum),
                        MockMvcResultMatchers.jsonPath("$[0].studentNum.number").value(lateRankHttpResponse.studentNum.number),
                        MockMvcResultMatchers.jsonPath("$[0].profileUrl").value(lateRankHttpResponse.profileUrl)
                    )
            }
        }
    }
})