package com.goms.v2.domain.studentCouncil

import com.goms.v2.domain.studentCouncil.usecase.CreateOutingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/student-council")
class StudentCouncilController(
	private val createOutingUseCase: CreateOutingUseCase
) {

	@PostMapping("outing")
	fun createOuting(): ResponseEntity<Map<String, UUID>> =
		createOutingUseCase.execute()
			.let { ResponseEntity.ok(mapOf("outingUUID" to it)) }
}