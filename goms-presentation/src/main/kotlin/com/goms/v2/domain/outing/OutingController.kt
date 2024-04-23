package com.goms.v2.domain.outing

import com.goms.v2.domain.outing.dto.response.OutingAccountHttpResponse
import com.goms.v2.domain.outing.dto.response.OutingCountHttpResponse
import com.goms.v2.domain.outing.mapper.OutingDataMapper
import com.goms.v2.domain.outing.usecase.*
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
    private val searchOutingAccountUseCase: SearchOutingAccountUseCase,
    private val validateOutingTimeUseCase: ValidateOutingTimeUseCase
) {

    @PostMapping("{outingUUID}")
    fun outing(@PathVariable outingUUID: UUID): ResponseEntity<Void> =
        outingUseCase.execute(outingUUID)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

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
    fun searchOutingAccount(@RequestParam(required = false) name: String?): ResponseEntity<List<OutingAccountHttpResponse>> =
        searchOutingAccountUseCase.execute(name)
            .map { outingDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it)}

    @GetMapping("validation")
    fun validateOuting(): ResponseEntity<Map<String, Boolean>> =
        validateOutingTimeUseCase.execute()
            .let { ResponseEntity.ok(mapOf("isOuting" to it)) }

}
