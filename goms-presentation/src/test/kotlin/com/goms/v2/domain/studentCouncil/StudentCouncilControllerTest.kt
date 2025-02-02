package com.goms.v2.domain.studentCouncil

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.GrantAuthorityDto
import com.goms.v2.domain.studentCouncil.data.dto.LateAccountDto
import com.goms.v2.domain.studentCouncil.data.dto.SearchAccountDto
import com.goms.v2.domain.studentCouncil.dto.request.GrantAuthorityHttpRequest
import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import com.goms.v2.domain.studentCouncil.dto.response.LateAccountHttpResponse
import com.goms.v2.domain.studentCouncil.mapper.StudentCouncilDataMapper
import com.goms.v2.domain.studentCouncil.usecase.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDate
import java.util.*

class StudentCouncilControllerTest: DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    lateinit var mockMvc: MockMvc
    val createOutingUseCase = mockk<CreateOutingUseCase>()
    val saveOutingBlackListUseCase = mockk<SaveOutingBlackListUseCase>()
    val deleteOutingBlackListUseCase = mockk<DeleteOutingBlacklistUseCase>()
    val queryAllAccountUseCase = mockk<QueryAllAccountUseCase>()
    val studentCouncilDataMapper = mockk<StudentCouncilDataMapper>()
    val grantAuthorityUseCase = mockk<GrantAuthorityUseCase>()
    val searchAccountUseCase = mockk<SearchAccountUseCase>()
    val deleteOutingUseCase = mockk<DeleteOutingUseCase>()
    val getLateAccountUseCase = mockk<GetLateAccountUseCase>()
    val forcingOutingUseCase = mockk<ForcingOutingUseCase>()
    val studentCouncilController = StudentCouncilController(
        createOutingUseCase,
        saveOutingBlackListUseCase,
        deleteOutingBlackListUseCase,
        queryAllAccountUseCase,
        studentCouncilDataMapper,
        grantAuthorityUseCase,
        searchAccountUseCase,
        deleteOutingUseCase,
        getLateAccountUseCase,
        forcingOutingUseCase
    )

    beforeTest {
        mockMvc = MockMvcBuilders.standaloneSetup(studentCouncilController).build()
    }

    describe("/api/v2/student-council/outing 으로 POST 요청을 했을때")  {
        val url = "/api/v2/student-council/outing"

        context("유효한 요청이 전달 되면") {
            val outingUUID = UUID.randomUUID()
            every { createOutingUseCase.execute() } returns outingUUID

            it("outingUUID를 응답해야 한다.") {
                mockMvc.perform(
                    post(url)
                )
                    .andExpectAll(
                        status().isOk,
                        jsonPath("outingUUID").value(outingUUID.toString())
                    )
            }
        }
    }

    describe("/api/v2/student-council/black-list/{accountIdx} 으로 POST 요청을 했을때") {
        val url = "/api/v2/student-council/black-list/{accountIdx}"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            every { saveOutingBlackListUseCase.execute(accountIdx) } returns Unit

            it("201 status code를 응답해야한다.") {
                mockMvc.perform(
                    post(url, accountIdx)
                )
                    .andExpect(status().`is`(201))
            }
        }
    }

    describe("/api/v2/student-council/black-list/{accountIdx} 으로 DELETE 요청을 했을때") {
        val url = "/api/v2/student-council/black-list/{accountIdx}"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            every { deleteOutingBlackListUseCase.execute(accountIdx) } returns Unit

            it("205 status code를 응답해야한다.") {
                mockMvc.perform(
                    delete(url, accountIdx)
                )
                    .andExpect(status().`is`(205))
            }
        }
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

    describe("/api/v2/student-council/accounts 으로 GET 요청을 했을때") {
        val url = "/api/v2/student-council/accounts"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            val accountDto = AccountDto(
                accountIdx = accountIdx,
                name = "",
                grade = 6,
                gender = Gender.MAN,
                major = Major.SMART_IOT,
                profileUrl = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true,
                outing = false
            )
            val allAccountHttpResponse = AllAccountHttpResponse(
                accountIdx = accountIdx,
                name = "",
                grade = 6,
                gender = Gender.MAN,
                major = Major.SMART_IOT,
                profileUrl = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true,
                isOuting = false
            )
            every { queryAllAccountUseCase.execute() } returns listOf(accountDto)
            every { studentCouncilDataMapper.toResponse(accountDto) } returns allAccountHttpResponse

            it("List<AllAccountResponse>를 응답해야한다.") {
                mockMvc.perform(
                    get(url)
                )
                    .andExpectAll(
                        status().isOk,
                        jsonPath("$[0].accountIdx").value(allAccountHttpResponse.accountIdx.toString()),
                        jsonPath("$[0].name").value(allAccountHttpResponse.name),
                        jsonPath("$[0].grade").value(allAccountHttpResponse.grade),
                        jsonPath("$[0].gender").value(allAccountHttpResponse.gender.toString()),
                        jsonPath("$[0].major").value(allAccountHttpResponse.major.toString()),
                        jsonPath("$[0].profileUrl").value(allAccountHttpResponse.profileUrl),
                        jsonPath("$[0].authority").value(allAccountHttpResponse.authority.toString()),
                        jsonPath("$[0].isBlackList").value(allAccountHttpResponse.isBlackList.toString())
                    )
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("/api/v2/student-council/search 으로 GET 요청을 했을때") {
        val url = "/api/v2/student-council/search"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            val grade = 0
            val gender = Gender.MAN
            val name = ""
            val authority = Authority.ROLE_STUDENT
            val isBlackList = true
            val major = Major.SMART_IOT

            val searchAccountDto = SearchAccountDto(
                grade = 0,
                gender = Gender.MAN,
                name = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true,
                major = Major.SMART_IOT
            )


            val requestParam = LinkedMultiValueMap<String, String>()
            requestParam.add("grade", "0")
            requestParam.add("gender", Gender.MAN.toString())
            requestParam.add("name", "")
            requestParam.add("authority", Authority.ROLE_STUDENT.toString())
            requestParam.add("isBlackList", true.toString())
            requestParam.add("major", Major.SMART_IOT.toString())

            val accountDto = AccountDto(
                accountIdx = accountIdx,
                name = "",
                grade = 0,
                gender = Gender.MAN,
                major = Major.SMART_IOT,
                profileUrl = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true,
                outing = false
            )

            val allAccountHttpResponse = AllAccountHttpResponse(
                accountIdx = accountIdx,
                name = "",
                grade = 0,
                gender = Gender.MAN,
                major = Major.SMART_IOT,
                profileUrl = "",
                authority = Authority.ROLE_STUDENT,
                isBlackList = true,
                isOuting = false
            )

            every { studentCouncilDataMapper.toDto(grade, gender , name, authority, isBlackList, major) } returns searchAccountDto
            every { searchAccountUseCase.execute(searchAccountDto) } returns listOf(accountDto)
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
                        jsonPath("$[0].grade").value(allAccountHttpResponse.grade),
                        jsonPath("$[0].gender").value(allAccountHttpResponse.gender.toString()),
                        jsonPath("$[0].major").value(allAccountHttpResponse.major.toString()),
                        jsonPath("$[0].profileUrl").value(allAccountHttpResponse.profileUrl),
                        jsonPath("$[0].authority").value(allAccountHttpResponse.authority.toString()),
                        jsonPath("$[0].isBlackList").value(allAccountHttpResponse.isBlackList.toString())
                    )
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("/api/v2/student-council/outing/{accountIdx} 으로 DELETE 요청을 했을때") {
        val url = "/api/v2/student-council/outing/{accountIdx}"

        context("유효한 요청이 전달 되면") {
            val accountIdx = UUID.randomUUID()
            every { deleteOutingUseCase.execute(accountIdx) } returns Unit

            it("205 status code를 응답해야한다.") {
                mockMvc.perform(
                    delete(url, accountIdx)
                )
                    .andExpect(status().`is`(205))
            }
        }
    }

    describe("/api/v2/student-council/late 으로 GET 요청을 했을때") {
        var url = "/api/v2/student-council/late"

        context("유효한 요청이 전달되면 ") {
            val localDate = LocalDate.parse("2024-02-04")
            val accountIdx = UUID.randomUUID()
            val lateAccountDto = LateAccountDto(
                accountIdx = accountIdx,
                name = "",
                grade = 6,
                gender = Gender.MAN,
                major = Major.SMART_IOT,
                profileUrl = "",
            )
            val lateAccountHttpResponse = LateAccountHttpResponse(
                accountIdx = accountIdx,
                name = "",
                grade = 6,
                gender = Gender.MAN,
                major = Major.SMART_IOT,
                profileUrl = ""
            )

            val requestParam = LinkedMultiValueMap<String, String>()
            requestParam.add("date", "2024-02-04")

            every { getLateAccountUseCase.execute(localDate) } returns listOf(lateAccountDto)
            every { studentCouncilDataMapper.toResponse(lateAccountDto) } returns lateAccountHttpResponse

            it ("List<LateAccountHttpResponse>를 반환한다.") {
                mockMvc.perform(
                    get(url)
                        .params(requestParam)
                )
                    .andExpectAll(
                        status().isOk,
                        jsonPath("$[0].accountIdx").value(lateAccountHttpResponse.accountIdx.toString()),
                        jsonPath("$[0].name").value(lateAccountHttpResponse.name),
                        jsonPath("$[0].grade").value(lateAccountHttpResponse.grade),
                        jsonPath("$[0].gender").value(lateAccountHttpResponse.gender.toString()),
                        jsonPath("$[0].major").value(lateAccountHttpResponse.major.toString()),
                        jsonPath("$[0].profileUrl").value(lateAccountHttpResponse.profileUrl)
                    )
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    describe("/api/v2/student-council/outing/{outingIdx} 으로 POST 요청을 했을때") {
        val url = "/api/v2/student-council/outing/{outingIdx}"

        context("유효한 요청이 전달 되면") {
            val outingIdx = UUID.randomUUID()
            every { forcingOutingUseCase.execute(outingIdx) } returns Unit

            it("201 status code를 응답해야한다.") {
                mockMvc.perform(
                    post(url, outingIdx)
                )
                    .andExpect(status().`is`(201))
            }
        }
    }
})
