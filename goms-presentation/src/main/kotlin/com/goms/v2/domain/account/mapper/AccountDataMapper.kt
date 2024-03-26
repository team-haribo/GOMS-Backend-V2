package com.goms.v2.domain.account.mapper

import com.goms.v2.domain.account.data.dto.ChangePasswordDto
import com.goms.v2.domain.account.data.dto.PasswordDto
import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.dto.request.ChangePasswordRequest
import com.goms.v2.domain.account.dto.request.UpdatePasswordRequest
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import org.springframework.stereotype.Component

@Component
class AccountDataMapper {

    fun toResponse(profileDto: ProfileDto) =
        ProfileHttpResponse(
            name = profileDto.name,
            grade = profileDto.grade,
            gender = profileDto.gender,
            major = profileDto.major,
            authority = profileDto.authority,
            profileUrl = profileDto.profileUrl,
            lateCount = profileDto.lateCount,
            isOuting = profileDto.isOuting,
            isBlackList = profileDto.isBlackList
        )

    fun toDomain(updatePasswordRequest: UpdatePasswordRequest) =
        PasswordDto(
            email = updatePasswordRequest.email,
            newPassword = updatePasswordRequest.newPassword
        )

    fun toDomain(changePasswordRequest: ChangePasswordRequest) =
        ChangePasswordDto(
            password = changePasswordRequest.password,
            newPassword = changePasswordRequest.newPassword
        )

}