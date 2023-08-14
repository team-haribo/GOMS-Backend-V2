package com.goms.v2.persistence.late.repository

import com.goms.v2.domain.late.Late
import com.goms.v2.persistence.late.mapper.LateMapper
import com.goms.v2.repository.late.LateRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class LateRepositoryImpl(
    private val lateJpaRepository: LateJpaRepository,
    private val lateMapper: LateMapper
): LateRepository {

    override fun countByAccountIdx(accountIdx: UUID): Long =
        lateJpaRepository.countByAccountIdx(accountIdx)

    override fun saveAll(lateList: List<Late>) {
        lateList
            .map { lateMapper.toEntity(it) }
            .let { lateJpaRepository.saveAll(it) }
    }

}