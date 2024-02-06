package com.goms.v2.domain.auth

import com.goms.v2.domain.auth.dto.request.SendAuthCodeHttpRequest
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.request.SignUpHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthCodeDataMapper
import com.goms.v2.domain.auth.mapper.AuthDataMapper
import com.goms.v2.domain.auth.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v2/auth")
class AuthController(
    private val authDataMapper: AuthDataMapper,
    private val sendAuthCodeDataMapper: AuthCodeDataMapper,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val sendAuthCodeUseCase: SendAuthCodeUseCase,
    private val verifyAuthCodeUseCase: VerifyAuthCodeUseCase,
    private val logoutUseCase: LogoutUseCase
) {

    @PostMapping("signup")
    fun signUp(@RequestBody @Valid signUpHttpRequest: SignUpHttpRequest): ResponseEntity<Void> =
        signUpUseCase.execute(authDataMapper.toDto(signUpHttpRequest))
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PostMapping("signin")
    fun signIn(@RequestBody @Valid signInHttpRequest: SignInHttpRequest): ResponseEntity<TokenHttpResponse> =
        signInUseCase.execute(authDataMapper.toDto(signInHttpRequest))
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }


    @PatchMapping
    fun reissue(@RequestHeader refreshToken: String): ResponseEntity<TokenHttpResponse> =
        reissueTokenUseCase.execute(refreshToken)
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }

    @PostMapping("email/send")
    fun sendAuthCode(@RequestBody request: SendAuthCodeHttpRequest): ResponseEntity<Void> =
        sendAuthCodeUseCase.execute(sendAuthCodeDataMapper.toDto(request))
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @GetMapping("/email/verify")
    fun verifyAuthCode(@RequestParam email: String, @RequestParam authCode: String): ResponseEntity<Void> =
        verifyAuthCodeUseCase.execute(email, authCode)
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @DeleteMapping("logout")
    fun logout(@RequestHeader refreshToken: String): ResponseEntity<Void> =
        logoutUseCase.execute(refreshToken)
            .let { ResponseEntity.ok().build() }

}