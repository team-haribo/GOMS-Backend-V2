package com.goms.v2.persistence.auth.repository

import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.persistence.auth.mapper.RefreshTokenMapper
import com.goms.v2.repository.auth.RefreshTokenRepository
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepositoryImpl(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
    private val refreshTokenMapper: RefreshTokenMapper
): RefreshTokenRepository{

    override fun save(refreshToken: RefreshToken) {
        val refreshTokenEntity = refreshTokenMapper.toEntity(refreshToken)
        refreshTokenJpaRepository.save(refreshTokenEntity)

    }

}
