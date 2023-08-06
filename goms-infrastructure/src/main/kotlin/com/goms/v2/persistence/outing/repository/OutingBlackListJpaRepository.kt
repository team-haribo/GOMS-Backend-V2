package com.goms.v2.persistence.outing.repository

import com.goms.v2.persistence.outing.entity.OutingBlackListRedisEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface OutingBlackListJpaRepository: CrudRepository<OutingBlackListRedisEntity, UUID>