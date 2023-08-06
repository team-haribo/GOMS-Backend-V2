package com.goms.v2.persistence.email.repository

import com.goms.v2.persistence.email.entity.AuthenticationRedisEntity
import org.springframework.data.repository.CrudRepository

interface AuthenticationRedisRepository: CrudRepository<AuthenticationRedisEntity, String>