package com.goms.v2.persistence.email.repository

import com.goms.v2.persistence.email.entity.EmailAuthRedisEntity
import org.springframework.data.repository.CrudRepository

interface EmailAuthRedisRepository: CrudRepository<EmailAuthRedisEntity, String>