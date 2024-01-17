package com.goms.v2.domain.studentCouncil.mapper

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.GrantAuthorityDto
import com.goms.v2.domain.studentCouncil.data.dto.SearchAccountDto
import com.goms.v2.domain.studentCouncil.dto.request.GrantAuthorityHttpRequest
import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import org.mapstruct.*
import org.springframework.stereotype.Component

@Component
class StudentCouncilDataMapper {

    fun toResponse(accountDto: AccountDto) =
        AllAccountHttpResponse(
            accountIdx = accountDto.accountIdx,
            name = accountDto.name,
            grade = accountDto.grade,
            gender = accountDto.gender,
            profileUrl = accountDto.profileUrl,
            authority = accountDto.authority,
            isBlackList = accountDto.isBlackList
        )

    fun toDto(request: GrantAuthorityHttpRequest) =
        GrantAuthorityDto(
            accountIdx = request.accountIdx,
            authority = request.authority
        )

    fun toResponse(dto: List<AccountDto>) =
        dto.map {
            AllAccountHttpResponse(
                accountIdx = it.accountIdx,
                name = it.name,
                grade = it.grade,
                gender = it.gender,
                profileUrl = it.profileUrl,
                authority = it.authority,
                isBlackList = it.isBlackList
            )
        }

    fun toDto(
        grade: Int?,
        gender: Gender,
        name: String?,
        authority: Authority?,
        isBlackList: Boolean?
    ) = SearchAccountDto(
            grade = grade,
            gender = gender,
            name = name,
            authority = authority,
            isBlackList = isBlackList
        )

}