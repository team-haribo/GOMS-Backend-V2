package com.goms.v2.domain.studentCouncil.data.dto

import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.data.dto.StudentNumberDto
import java.util.UUID

data class AccountDto(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumberDto,
    val profileUrl: String?,
    val authority: Authority,
    val isBlackList: Boolean
)
