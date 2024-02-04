package com.goms.v2.domain.studentCouncil

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.studentCouncil.dto.request.GrantAuthorityHttpRequest
import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import com.goms.v2.domain.studentCouncil.dto.response.LateAccountHttpResponse
import com.goms.v2.domain.studentCouncil.mapper.StudentCouncilDataMapper
import com.goms.v2.domain.studentCouncil.usecase.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("/api/v2/student-council")
class StudentCouncilController(
    private val createOutingUseCase: CreateOutingUseCase,
    private val saveOutingBlackListUseCase: SaveOutingBlackListUseCase,
    private val deleteOutingBlacklistUseCase: DeleteOutingBlacklistUseCase,
    private val queryAllAccountUseCase: QueryAllAccountUseCase,
    private val studentCouncilDataMapper: StudentCouncilDataMapper,
    private val grantAuthorityUseCase: GrantAuthorityUseCase,
    private val searchAccountUseCase: SearchAccountUseCase,
    private val deleteOutingUseCase: DeleteOutingUseCase,
    private val getsLatecomerAccountUseCase: GetsLatecomerAccountUseCase
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

    @GetMapping("accounts")
    fun queryAllAccount(): ResponseEntity<List<AllAccountHttpResponse>> =
        queryAllAccountUseCase.execute()
            .map { studentCouncilDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @PatchMapping("authority")
    fun grantAuthority(@RequestBody request: GrantAuthorityHttpRequest): ResponseEntity<Void> =
        studentCouncilDataMapper.toDto(request)
            .let { grantAuthorityUseCase.execute(it) }
            .let { ResponseEntity.status(HttpStatus.RESET_CONTENT).build() }

    @GetMapping("search")
    fun searchAccount(
        @RequestParam(required = false) grade: Int?,
        @RequestParam(required = false) gender: Gender,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) authority: Authority?,
        @RequestParam(required = false) isBlackList: Boolean?
    ): ResponseEntity<List<AllAccountHttpResponse>> =
        studentCouncilDataMapper.toDto(grade, gender, name, authority, isBlackList)
            .let { searchAccountUseCase.execute(it) }
            .let { studentCouncilDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("outing/{accountIdx}")
    fun deleteOuting(@PathVariable accountIdx: UUID): ResponseEntity<Void> =
        deleteOutingUseCase.execute(accountIdx)
            .let { ResponseEntity.status(HttpStatus.RESET_CONTENT).build() }

    @GetMapping("late")
    fun getLatecomer(
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<List<LateAccountHttpResponse>> =
        getsLatecomerAccountUseCase.execute(date)
            .map { studentCouncilDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }
}