package com.goms.v2.persistence.studentCouncil.repository

import com.goms.v2.persistence.studentCouncil.entity.OutingUUIDRedisEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface OutingUUIDJpaRepository: CrudRepository<OutingUUIDRedisEntity, UUID>