package com.goms.v2.repository.late

import java.util.*

interface LateRepository {

    fun countByAccountIdx(accountIdx: UUID): Long

}