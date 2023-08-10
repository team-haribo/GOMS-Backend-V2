package com.goms.v2.persistence.late.repository

import com.goms.v2.repository.late.LateRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class LateRepositoryImpl(
    private val lateJpaRepository: LateJpaRepository
): LateRepository {

    override fun countByAccountIdx(accountIdx: UUID): Long =
        lateJpaRepository.countByAccountIdx(accountIdx)

}