package com.goms.v2.persistence.auth.repository

import com.goms.v2.persistence.auth.entity.RefreshTokenJpaEntity
import org.springframework.data.repository.CrudRepository

interface RefreshTokenJpaRepository: CrudRepository<RefreshTokenJpaEntity, String>