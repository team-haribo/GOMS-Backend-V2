package com.goms.v2.persistence.late.repository

import com.goms.v2.persistence.late.entity.LateJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface LateJpaRepository: CrudRepository<LateJpaEntity, UUID> {

    fun countByAccountIdx(accountIdx: UUID): Long

}