package com.goms.v2.domain.auth

import com.goms.v2.domain.auth.dto.request.SignInWebRequest
import com.goms.v2.domain.auth.dto.response.TokenInResponse
import com.goms.v2.domain.auth.dto.response.TokenWebResponse
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
    fun signIn(@RequestBody @Valid signInWebRequest: SignInWebRequest): ResponseEntity<TokenWebResponse> =
        signInUseCase.execute(signInWebRequest.toData())
            .let { ResponseEntity.ok(it.toResponse()) }

    private fun TokenInResponse.toResponse(): TokenWebResponse =
        TokenWebResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExp = accessTokenExp,
            refreshTokenExp = refreshTokenExp,
            authority = authority
        )

}