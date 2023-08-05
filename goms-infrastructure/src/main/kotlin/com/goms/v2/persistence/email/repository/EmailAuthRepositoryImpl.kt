package com.goms.v2.persistence.email.repository

import com.goms.v2.domain.email.EmailAuth
import com.goms.v2.persistence.account.repository.AccountJpaRepository
import com.goms.v2.persistence.email.mapper.EmailAuthMapper
import com.goms.v2.repository.email.EmailAuthRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class EmailAuthRepositoryImpl(
    private val emailAuthRedisRepository: EmailAuthRedisRepository,
    private val accountJpaRepository: AccountJpaRepository,
    private val emailAuthMapper: EmailAuthMapper
): EmailAuthRepository {

    override fun save(emailAuth: EmailAuth) {
        emailAuthRedisRepository.save(emailAuthMapper.toEntity(emailAuth))
    }

    override fun findByIdOrNull(email: String): EmailAuth? =
        emailAuthMapper.toDomain(emailAuthRedisRepository.findByIdOrNull(email))

    override fun existByEmail(email: String): Boolean =
        accountJpaRepository.existsByEmail(email)

}