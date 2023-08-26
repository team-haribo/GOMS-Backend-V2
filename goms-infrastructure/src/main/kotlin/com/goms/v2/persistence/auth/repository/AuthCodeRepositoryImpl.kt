package com.goms.v2.persistence.auth.repository

import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.persistence.auth.mapper.AuthCodeMapper
import com.goms.v2.repository.auth.AuthCodeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AuthCodeRepositoryImpl(
    private val authCodeRedisRepository: AuthCodeRedisRepository,
    private val authCodeMapper: AuthCodeMapper
): AuthCodeRepository {

    override fun save(authCode: AuthCode) {
        authCodeRedisRepository.save(authCodeMapper.toEntity(authCode))
    }

    override fun findByIdOrNull(email: String): AuthCode? =
        authCodeMapper.toDomain(authCodeRedisRepository.findByIdOrNull(email))

}