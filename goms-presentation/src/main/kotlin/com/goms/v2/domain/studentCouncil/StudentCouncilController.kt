package com.goms.v2.domain.studentCouncil

import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import com.goms.v2.domain.studentCouncil.mapper.StudentCouncilDataMapper
import com.goms.v2.domain.studentCouncil.usecase.CreateOutingUseCase
import com.goms.v2.domain.studentCouncil.usecase.DeleteOutingBlacklistUseCase
import com.goms.v2.domain.studentCouncil.usecase.QueryAllAccountUseCase
import com.goms.v2.domain.studentCouncil.usecase.SaveOutingBlackListUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v2/student-council")
class StudentCouncilController(
    private val createOutingUseCase: CreateOutingUseCase,
    private val saveOutingBlackListUseCase: SaveOutingBlackListUseCase,
    private val deleteOutingBlacklistUseCase: DeleteOutingBlacklistUseCase,
    private val queryAllAccountUseCase: QueryAllAccountUseCase,
    private val studentCouncilDataMapper: StudentCouncilDataMapper
) {

    @PostMapping("outing")
    fun createOuting(): ResponseEntity<Map<String, UUID>> =
        createOutingUseCase.execute()
            .let { ResponseEntity.ok(mapOf("outingUUID" to it)) }

    @PostMapping("black-list/{accountIdx}")
    fun saveBlackList(@PathVariable accountIdx: UUID): ResponseEntity<Void> =
        saveOutingBlackListUseCase.execute(accountIdx)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @DeleteMapping("black-list/{accountIdx}")
    fun deleteBlackList(@PathVariable accountIdx: UUID): ResponseEntity<Void> =
        deleteOutingBlacklistUseCase.execute(accountIdx)
            .let { ResponseEntity.status(HttpStatus.RESET_CONTENT).build() }

    @GetMapping("account")
    fun queryAllAccount(): ResponseEntity<List<AllAccountHttpResponse>> =
        queryAllAccountUseCase.execute()
            .map { studentCouncilDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }

}