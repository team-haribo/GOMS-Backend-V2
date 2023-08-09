package com.goms.v2.domain.outing

import com.goms.v2.domain.outing.usecase.OutingUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/v2/outing")
class OutingController(
    private val outingUseCase: OutingUseCase
) {

    @PostMapping("{outingUUID}")
    fun outing(@PathVariable outingUUID: UUID): ResponseEntity<Void> =
        outingUseCase.execute(outingUUID)
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

}