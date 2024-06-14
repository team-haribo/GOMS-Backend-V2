package com.goms.v2.repository.studentCouncil

import com.goms.v2.domain.studentCouncil.OutingUUID
import java.util.UUID

interface OutingUUIDRepository {

    fun save(outingUUID: OutingUUID): UUID
    fun existsById(outingUUID: UUID): Boolean

}