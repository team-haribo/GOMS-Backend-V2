package com.goms.v2.domain.account

import com.goms.v2.domain.account.dto.request.UpdatePasswordRequest
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import com.goms.v2.domain.account.mapper.AccountDataMapper
import com.goms.v2.domain.account.usecase.QueryAccountProfileUseCase
import com.goms.v2.domain.account.usecase.UpdatePasswordUseCase
import com.goms.v2.domain.account.usecase.UploadImageUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("api/v2/account")
class AccountController(
    private val accountDataMapper: AccountDataMapper,
    private val queryAccountProfileUseCase: QueryAccountProfileUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) {

    @GetMapping("profile")
    fun queryProfile(): ResponseEntity<ProfileHttpResponse> =
        queryAccountProfileUseCase.execute()
            .let { ResponseEntity.ok(accountDataMapper.toResponse(it)) }

    @PatchMapping("new-password")
    fun updatePassword(@RequestBody updatePasswordRequest: UpdatePasswordRequest): ResponseEntity<Void> =
        updatePasswordUseCase.execute(accountDataMapper.toDomain(updatePasswordRequest))
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PostMapping("image")
    fun uploadImage(@RequestPart image: MultipartFile): ResponseEntity<Void> =
        uploadImageUseCase.execute(image)
                .let { ResponseEntity.ok().build() }

}