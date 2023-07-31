package com.goms.v2.domain.auth

import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthDataMapper
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
    private val authDataMapper: AuthDataMapper,
    private val signInUseCase: SignInUseCase,
) {

    @PostMapping("/signin")
    fun signIn(@RequestBody @Valid signInHttpRequest: SignInHttpRequest): ResponseEntity<TokenHttpResponse> =
        signInUseCase.execute(authDataMapper.toDto(signInHttpRequest))
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }

}