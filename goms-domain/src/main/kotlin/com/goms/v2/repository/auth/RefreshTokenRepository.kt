package com.goms.v2.repository.auth

import com.goms.v2.domain.auth.RefreshToken
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository {
    fun save(refreshToken: RefreshToken)
}