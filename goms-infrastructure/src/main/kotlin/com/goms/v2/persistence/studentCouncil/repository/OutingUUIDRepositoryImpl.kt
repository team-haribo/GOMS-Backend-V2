package com.goms.v2.persistence.studentCouncil.repository

import com.goms.v2.domain.studentCouncil.OutingUUID
import com.goms.v2.persistence.studentCouncil.mapper.OutingUUIDMapper
import com.goms.v2.repository.studentCouncil.OutingUUIDRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OutingUUIDRepositoryImpl(
    private val outingUUIDJpaRepository: OutingUUIDJpaRepository,
    private val outingUUIDMapper: OutingUUIDMapper
): OutingUUIDRepository {

    override fun save(outingUUID: OutingUUID): UUID {
        val outingUUIDEntity = outingUUIDMapper.toEntity(outingUUID)
        return outingUUIDJpaRepository.save(outingUUIDEntity).outingUUID
    }

    override fun existsById(outingUUID: UUID): Boolean {
        return outingUUIDJpaRepository.existsById(outingUUID)
    }

}