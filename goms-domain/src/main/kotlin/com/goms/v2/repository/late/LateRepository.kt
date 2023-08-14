package com.goms.v2.repository.late

import com.goms.v2.domain.late.Late
import java.util.*

interface LateRepository {

    fun countByAccountIdx(accountIdx: UUID): Long
    fun saveAll(lateList: List<Late>)

}