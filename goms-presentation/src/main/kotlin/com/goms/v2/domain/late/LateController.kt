package com.goms.v2.domain.late

import com.goms.v2.domain.late.dto.LateRankHttpResponse
import com.goms.v2.domain.late.mapper.LateDataMapper
import com.goms.v2.domain.late.usecase.QueryLateRankUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/late")
class LateController(
    private val lateDataMapper: LateDataMapper,
    private val queryLateRankUseCase: QueryLateRankUseCase
) {

    @GetMapping("rank")
    fun queryLateRank(): ResponseEntity<List<LateRankHttpResponse>> =
        queryLateRankUseCase.execute()
            .map { lateDataMapper.toResponse(it) }
            .let { ResponseEntity.ok(it) }

}