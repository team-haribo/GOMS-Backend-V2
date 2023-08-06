package com.goms.v2.persistence.email.repository

import com.goms.v2.domain.email.Authentication
import com.goms.v2.persistence.email.mapper.AuthenticationMapper
import com.goms.v2.repository.email.AuthenticationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AuthenticationRepositoryImpl(
    private val authenticationRedisRepository: AuthenticationRedisRepository,
    private val authenticationMapper: AuthenticationMapper
): AuthenticationRepository {

    override fun save(authentication: Authentication) {
        authenticationMapper.toEntity(authentication)
            .let { authenticationRedisRepository.save(it) }
    }

    override fun existByEmail(email: String): Boolean =
        authenticationRedisRepository.existsById(email)

    override fun findByIdOrNull(email: String): Authentication? =
        authenticationRedisRepository.findByIdOrNull(email)
            .let { authenticationMapper.toDomain(it) }

}