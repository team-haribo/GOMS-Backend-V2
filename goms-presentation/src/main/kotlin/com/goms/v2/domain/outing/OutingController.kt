package com.goms.v2.domain.outing

import com.goms.v2.domain.outing.dto.response.OutingAccountHttpResponse
import com.goms.v2.domain.outing.dto.response.OutingCountHttpResponse
import com.goms.v2.domain.outing.mapper.OutingDataMapper
import com.goms.v2.domain.outing.usecase.OutingUseCase
import com.goms.v2.domain.outing.usecase.QueryOutingAccountUseCase
import com.goms.v2.domain.outing.usecase.QueryOutingCountUseCase
import com.goms.v2.domain.outing.usecase.SearchOutingUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/v2/outing")
class OutingController(
    private val outingDataMapper: OutingDataMapper,
    private val outingUseCase: OutingUseCase,
    private val queryOutingAccountUseCase: QueryOutingAccountUseCase,
    private val queryOutingCountUseCase: QueryOutingCountUseCase,
    private val searchOutingUseCase: SearchOutingUseCase
) {

    @PostMapping("{outingUUID}")
    fun outing(@PathVariable outingUUID: UUID): ResponseEntity<Void> =
        outingUseCase.execute(outingUUID)
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @GetMapping
    fun queryOutingAccount(): ResponseEntity<List<OutingAccountHttpResponse>> =
        queryOutingAccountUseCase.execute()
            .map { outingDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("count")
    fun queryOutingCount(): ResponseEntity<OutingCountHttpResponse> =
        queryOutingCountUseCase.execute()
            .let { outingDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("search")
    fun searchOutingAccount(name: String?): ResponseEntity<List<OutingAccountHttpResponse>> =
        searchOutingUseCase.execute(name)
            .map { outingDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it)}

}