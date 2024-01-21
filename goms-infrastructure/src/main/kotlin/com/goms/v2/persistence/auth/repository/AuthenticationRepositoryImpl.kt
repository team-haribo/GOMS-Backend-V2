package com.goms.v2.persistence.auth.repository

import com.goms.v2.domain.auth.Authentication
import com.goms.v2.persistence.auth.mapper.AuthenticationMapper
import com.goms.v2.repository.auth.AuthenticationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AuthenticationRepositoryImpl(
    private val authenticationRedisRepository: AuthenticationRedisRepository,
    private val authenticationMapper: AuthenticationMapper
): AuthenticationRepository {

    override fun save(authentication: Authentication) {
        val authCodeEntity = authenticationMapper.toEntity(authentication)
        authenticationRedisRepository.save(authCodeEntity)
    }

    override fun existByEmail(email: String): Boolean {
        return authenticationRedisRepository.existsById(email)
    }

    override fun findByIdOrNull(email: String): Authentication? {
        val authenticationEntity = authenticationRedisRepository.findByIdOrNull(email)
        return authenticationMapper.toDomain(authenticationEntity)
    }

}