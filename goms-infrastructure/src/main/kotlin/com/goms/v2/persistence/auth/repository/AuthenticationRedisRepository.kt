package com.goms.v2.persistence.auth.repository

import com.goms.v2.persistence.auth.entity.AuthenticationRedisEntity
import org.springframework.data.repository.CrudRepository

interface AuthenticationRedisRepository: CrudRepository<AuthenticationRedisEntity, String> {
    fun findByEmail(email: String): AuthenticationRedisEntity
}