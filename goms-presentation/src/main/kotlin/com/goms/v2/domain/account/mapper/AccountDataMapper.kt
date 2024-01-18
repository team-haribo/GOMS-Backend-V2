package com.goms.v2.domain.account.mapper

import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import org.springframework.stereotype.Component

@Component
class AccountDataMapper {

    fun toResponse(profileDto: ProfileDto) =
        ProfileHttpResponse(
            name = profileDto.name,
            grade = profileDto.grade,
            gender = profileDto.gender,
            authority = profileDto.authority,
            profileUrl = profileDto.profileUrl,
            lateCount = profileDto.lateCount,
            isOuting = profileDto.isOuting,
            isBlackList = profileDto.isBlackList
        )

}