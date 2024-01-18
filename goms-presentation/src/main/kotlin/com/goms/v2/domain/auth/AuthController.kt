package com.goms.v2.domain.auth

import com.goms.v2.domain.auth.dto.request.SignUpHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.mapper.AuthDataMapper
import com.goms.v2.domain.auth.usecase.ReissueTokenUseCase
import com.goms.v2.domain.auth.usecase.SignUpUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v2/auth")
class AuthController(
    private val authDataMapper: AuthDataMapper,
    private val signUpUseCase: SignUpUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
) {

    @PostMapping("signup")
    fun signUp(@RequestBody signUpHttpRequest: SignUpHttpRequest): ResponseEntity<Void> =
        signUpUseCase.execute(authDataMapper.toDto(signUpHttpRequest))
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PatchMapping
    fun reissue(@RequestHeader refreshToken: String): ResponseEntity<TokenHttpResponse> =
        reissueTokenUseCase.execute(refreshToken)
            .let { ResponseEntity.ok(authDataMapper.toResponse(it)) }

}