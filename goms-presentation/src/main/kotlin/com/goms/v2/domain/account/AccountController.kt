package com.goms.v2.domain.account

import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import com.goms.v2.domain.account.mapper.AccountDataMapper
import com.goms.v2.domain.account.usecase.QueryAccountProfileUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/account")
class AccountController(
    private val accountDataMapper: AccountDataMapper,
    private val queryAccountProfileUseCase: QueryAccountProfileUseCase
) {

    @GetMapping("profile")
    fun queryProfile(): ResponseEntity<ProfileHttpResponse> =
        queryAccountProfileUseCase.execute()
            .let { ResponseEntity.ok(accountDataMapper.toResponse(it)) }

}