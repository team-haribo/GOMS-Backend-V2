package com.goms.v2.persistence.auth.repository

import com.goms.v2.persistence.auth.entity.AuthCodeRedisEntity
import org.springframework.data.repository.CrudRepository

interface AuthCodeRedisRepository: CrudRepository<AuthCodeRedisEntity, String>