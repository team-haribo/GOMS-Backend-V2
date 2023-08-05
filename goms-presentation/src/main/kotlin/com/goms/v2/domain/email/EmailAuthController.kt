package com.goms.v2.domain.email

import com.goms.v2.domain.email.dto.request.SendEmailHttpRequest
import com.goms.v2.domain.email.mapper.EmailAuthDataMapper
import com.goms.v2.domain.email.usecase.SendEmailUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/email")
class EmailAuthController(
    private val emailAuthDataMapper: EmailAuthDataMapper,
    private val sendEmailUseCase: SendEmailUseCase
) {

    @PostMapping("send")
    fun sendEmail(@RequestBody sendEmailHttpRequest: SendEmailHttpRequest): ResponseEntity<Void> =
        sendEmailUseCase.execute(emailAuthDataMapper.toDto(sendEmailHttpRequest))
            .let { ResponseEntity.status(HttpStatus.OK).build() }

}