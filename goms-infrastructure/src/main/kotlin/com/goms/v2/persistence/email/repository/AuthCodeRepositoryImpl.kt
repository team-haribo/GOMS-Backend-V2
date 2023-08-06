package com.goms.v2.persistence.email.repository

import com.goms.v2.domain.email.AuthCode
import com.goms.v2.persistence.email.mapper.AuthCodeMapper
import com.goms.v2.repository.email.AuthCodeRepository
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