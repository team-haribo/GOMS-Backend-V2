package com.goms.v2.domain.auth

import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenInResponse
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.usecase.SignInUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val signInUseCase: SignInUseCase,
) {

    @PostMapping("/signin")
    fun signIn(@RequestBody @Valid signInHttpRequest: SignInHttpRequest): ResponseEntity<TokenHttpResponse> =
        signInUseCase.execute(signInHttpRequest.toData())
            .let { ResponseEntity.ok(it.toResponse()) }

    private fun TokenInResponse.toResponse(): TokenHttpResponse =
        TokenHttpResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExp = accessTokenExp,
            refreshTokenExp = refreshTokenExp,
            authority = authority
        )

}