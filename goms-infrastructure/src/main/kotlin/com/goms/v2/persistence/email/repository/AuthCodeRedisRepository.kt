package com.goms.v2.persistence.email.repository

import com.goms.v2.persistence.email.entity.AuthCodeRedisEntity
import org.springframework.data.repository.CrudRepository

interface AuthCodeRedisRepository: CrudRepository<AuthCodeRedisEntity, String>