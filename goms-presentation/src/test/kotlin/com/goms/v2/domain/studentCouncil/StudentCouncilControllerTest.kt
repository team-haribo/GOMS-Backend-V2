package com.goms.v2.domain.studentCouncil

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.data.dto.StudentNumberDto
import com.goms.v2.domain.account.dto.response.StudentNumHttpResponse
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.GrantAuthorityDto
import com.goms.v2.domain.studentCouncil.dto.request.GrantAuthorityHttpRequest
import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import com.goms.v2.domain.studentCouncil.mapper.StudentCouncilDataMapper
import com.goms.v2.domain.studentCouncil.usecase.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import java.util.*

class StudentCouncilControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
    val createOutingUseCase = mockk<CreateOutingUseCase>()
    val saveOutingBlackListUseCase = mockk<SaveOutingBlackListUseCase>()
    val deleteOutingBlackListUseCase = mockk<DeleteOutingBlacklistUseCase>()
    val queryAllAccountUseCase = mockk<QueryAllAccountUseCase>()
    val studentCouncilDataMapper = mockk<StudentCouncilDataMapper>()
    val grantAuthorityUseCase = mockk<GrantAuthorityUseCase>()
    val searchAccountUseCase = mockk<SearchAccountUseCase>()
    val studentCouncilController = StudentCouncilController(
        createOutingUseCase,
        saveOutingBlackListUseCase,
        deleteOutingBlackListUseCase,
        queryAllAccountUseCase,
        studentCouncilDataMapper,
        grantAuthorityUseCase,
        searchAccountUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(studentCouncilController).build()
    }

    describe("/api/v2/student-council/authority 으로 PATCH 요청을 했을때") {
        val url = "/api/v2/student-council/authority"

        context("유효한 요청이 전달 되면") {
            val grantAuthorityHttpRequest = GrantAuthorityHttpRequest(UUID.randomUUID(), Authority.ROLE_STUDENT)
            every { studentCouncilDataMapper.toDto(grantAuthorityHttpRequest) } returns GrantAuthorityDto(grantAuthorityHttpRequest.accountIdx, grantAuthorityHttpRequest.authority)
            every { grantAuthorityUseCase.execute(GrantAuthorityDto(grantAuthorityHttpRequest.accountIdx, grantAuthorityHttpRequest.authority)) } returns Unit
            val jsonRequestBody = jacksonObjectMapper().writeValueAsString(grantAuthorityHttpRequest)

            it("205 status code를 응답해야한다.") {
                mockMvc.perform(
                    patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
                )
                    .andExpect(status().`is`(205))
            }
        }
    }

    describe("/api/v2/student-council/search 으로 GET 요청을 했을때") {
        val url = "/api/v2/student-council/search"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            val grade = 0
            val classNum = 0
            val name = ""
            val authority = Authority.ROLE_STUDENT
            val isBlackList = true

            val requestParam = LinkedMultiValueMap<String, String>()
            requestParam.add("grade", "0")
            requestParam.add("classNum", "0")
            requestParam.add("name", "")
            requestParam.add("authority", Authority.ROLE_STUDENT.toString())
            requestParam.add("isBlackList", true.toString())

            val accountDto = AccountDto(
                accountIdx = accountIdx,
                name = "",
                studentNum = StudentNumberDto(0, 0, 0),
                profileUrl = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true
            )

            val allAccountHttpResponse = AllAccountHttpResponse(
                accountIdx = accountIdx,
                name = "",
                studentNum = StudentNumHttpResponse(0, 0, 0),
                profileUrl = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true
            )

            every { searchAccountUseCase.execute(grade, classNum, name, authority, isBlackList) } returns listOf(accountDto)
            every { studentCouncilDataMapper.toResponse(listOf(accountDto)) } returns listOf(allAccountHttpResponse)

            it("List<AllAccountHttpResponse>를 응답해야한다.") {
                mockMvc.perform(
                    get(url)
                        .params(requestParam)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpectAll(
                        status().isOk,
                        jsonPath("$[0].accountIdx").value(allAccountHttpResponse.accountIdx.toString()),
                        jsonPath("$[0].name").value(allAccountHttpResponse.name),
                        jsonPath("$[0].studentNum.grade").value(allAccountHttpResponse.studentNum.grade),
                        jsonPath("$[0].studentNum.classNum").value(allAccountHttpResponse.studentNum.classNum),
                        jsonPath("$[0].studentNum.number").value(allAccountHttpResponse.studentNum.number),
                        jsonPath("$[0].profileUrl").value(allAccountHttpResponse.profileUrl),
                        jsonPath("$[0].authority").value(allAccountHttpResponse.authority.toString()),
                        jsonPath("$[0].isBlackList").value(allAccountHttpResponse.isBlackList.toString())
                    )
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

})
