package com.goms.v2.domain.outing

import com.goms.v2.domain.outing.mapper.OutingDataMapper
import com.goms.v2.domain.outing.usecase.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
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
})