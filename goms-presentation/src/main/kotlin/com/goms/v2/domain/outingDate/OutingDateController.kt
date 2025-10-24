package com.goms.v2.domain.outingDate

import com.goms.v2.domain.outingDate.mapper.OutingDateDataMapper
import com.goms.v2.domain.outingDate.usecase.SetTodayOutingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.goms.v2.domain.outingDate.dto.response.QueryTodayOutingHttpResponse
import com.goms.v2.domain.outingDate.dto.request.SetTodayOutingRequest
import com.goms.v2.domain.outingDate.usecase.ValidateTodayOutingUseCase

@RestController
@RequestMapping("api/v2/outing-date")
class OutingDateController(
	private val setTodayOutingUseCase: SetTodayOutingUseCase,
	private val validateTodayOutingUseCase: ValidateTodayOutingUseCase,
	private val outingDateDataMapper: OutingDateDataMapper
) {
	@PostMapping("today")
	fun setTodayOuting(
		@RequestBody setTodayOutingRequest: SetTodayOutingRequest,
		@RequestHeader("Discord-Client-Token") discordClientToken: String
	): ResponseEntity<Void> =
		setTodayOutingUseCase.execute(outingDateDataMapper.toDto(setTodayOutingRequest, discordClientToken))
			.run { ResponseEntity.ok().build() }

	@GetMapping("today")
	fun queryTodayOuting(): ResponseEntity<QueryTodayOutingHttpResponse> =
		validateTodayOutingUseCase.execute().let {
			ResponseEntity.ok(QueryTodayOutingHttpResponse(it))
		}
}
