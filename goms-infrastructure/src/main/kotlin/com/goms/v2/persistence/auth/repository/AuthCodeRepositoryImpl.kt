package com.goms.v2.persistence.auth.repository

import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.persistence.account.mapper.AuthCodeMapper
import com.goms.v2.repository.auth.AuthCodeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AuthCodeRepositoryImpl(
    private val authCodeRedisRepository: AuthCodeRedisRepository,
    private val authCodeMapper: AuthCodeMapper
): AuthCodeRepository {

    override fun save(authCode: AuthCode) {
        val authCodeEntity = authCodeMapper.toEntity(authCode)
        authCodeRedisRepository.save(authCodeEntity)
    }

    override fun findByIdOrNull(email: String): AuthCode? {
        val authCodeEntity = authCodeRedisRepository.findByIdOrNull(email)
        return authCodeMapper.toDomain(authCodeEntity)
    }

}