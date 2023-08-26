package com.goms.v2.domain.auth

import com.goms.v2.domain.auth.dto.request.SendEmailHttpRequest
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthDataMapper
import com.goms.v2.domain.auth.mapper.EmailAuthDataMapper
import com.goms.v2.domain.auth.usecase.ReissueTokenUseCase
import com.goms.v2.domain.auth.usecase.SendEmailUseCase
import com.goms.v2.domain.auth.usecase.SignInUseCase
import com.goms.v2.domain.auth.usecase.VerifyAuthCodeUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v2/auth")
class AuthController(
    private val authDataMapper: AuthDataMapper,
    private val emailAuthDataMapper: EmailAuthDataMapper,
    private val signInUseCase: SignInUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val sendEmailUseCase: SendEmailUseCase,
    private val verifyAuthCodeUseCase: VerifyAuthCodeUseCase
) {

    @PostMapping("/signin")
    fun signIn(@RequestBody @Valid signInHttpRequest: SignInHttpRequest): ResponseEntity<TokenHttpResponse> =
        signInUseCase.execute(authDataMapper.toDto(signInHttpRequest))
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }

    @PatchMapping
    fun reissue(@RequestHeader refreshToken: String): ResponseEntity<TokenHttpResponse> =
        reissueTokenUseCase.execute(refreshToken)
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }

    @PostMapping("/email/send")
    fun sendEmail(@RequestBody sendEmailHttpRequest: SendEmailHttpRequest): ResponseEntity<Void> =
        sendEmailUseCase.execute(emailAuthDataMapper.toDto(sendEmailHttpRequest))
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @GetMapping("/email/verify")
    fun verifyAuthCode(@RequestParam email: String, @RequestParam authCode: String): ResponseEntity<TokenHttpResponse> =
        verifyAuthCodeUseCase.execute(email, authCode)
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }

}