package com.goms.v2.domain.email

import com.goms.v2.domain.email.dto.request.SendEmailHttpRequest
import com.goms.v2.domain.email.mapper.EmailAuthDataMapper
import com.goms.v2.domain.email.usecase.SendEmailUseCase
import com.goms.v2.domain.email.usecase.VerifyAuthCodeUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2/email")
class EmailAuthController(
    private val emailAuthDataMapper: EmailAuthDataMapper,
    private val sendEmailUseCase: SendEmailUseCase,
    private val verifyAuthCodeUseCase: VerifyAuthCodeUseCase
) {

    @PostMapping("send")
    fun sendEmail(@RequestBody sendEmailHttpRequest: SendEmailHttpRequest): ResponseEntity<Void> =
        sendEmailUseCase.execute(emailAuthDataMapper.toDto(sendEmailHttpRequest))
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @GetMapping("verify")
    fun verifyAuthCode(@RequestParam email: String,@RequestParam authCode: String): ResponseEntity<Void> =
        verifyAuthCodeUseCase.execute(email, authCode)
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

}