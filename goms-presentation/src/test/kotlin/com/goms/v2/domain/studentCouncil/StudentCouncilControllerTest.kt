package com.goms.v2.domain.studentCouncil

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.studentCouncil.data.dto.GrantAuthorityDto
import com.goms.v2.domain.studentCouncil.dto.request.GrantAuthorityHttpRequest
import com.goms.v2.domain.studentCouncil.mapper.StudentCouncilDataMapper
import com.goms.v2.domain.studentCouncil.usecase.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class StudentCouncilControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val createOutingUseCase = mockk<CreateOutingUseCase>()
    val saveOutingBlackListUseCase = mockk<SaveOutingBlackListUseCase>()
    val deleteOutingBlackListUseCase = mockk<DeleteOutingBlacklistUseCase>()
    val queryAllAccountUseCase = mockk<QueryAllAccountUseCase>()
    val studentCouncilDataMapper = mockk<StudentCouncilDataMapper>()
    val grantAuthorityUseCase = mockk<GrantAuthorityUseCase>()
    val studentCouncilController = StudentCouncilController(
        createOutingUseCase,
        saveOutingBlackListUseCase,
        deleteOutingBlackListUseCase,
        queryAllAccountUseCase,
        studentCouncilDataMapper,
        grantAuthorityUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(studentCouncilController).build()
    }

    describe("PATCH : /api/v2/student-council/authority") {
        val url = "/api/v2/student-council/authority"

        context("유효한 요청이 전달 되면") {
            val grantAuthorityHttpRequest = GrantAuthorityHttpRequest(UUID.randomUUID(), Authority.ROLE_STUDENT)
            every { studentCouncilDataMapper.toDto(grantAuthorityHttpRequest) } returns GrantAuthorityDto(grantAuthorityHttpRequest.accountIdx, grantAuthorityHttpRequest.authority)
            every { grantAuthorityUseCase.execute(GrantAuthorityDto(grantAuthorityHttpRequest.accountIdx, grantAuthorityHttpRequest.authority)) } returns Unit
            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(grantAuthorityHttpRequest)

            it("205 응답") {
                mockMvc.perform(
                    patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(205))
            }
        }

    }

})
