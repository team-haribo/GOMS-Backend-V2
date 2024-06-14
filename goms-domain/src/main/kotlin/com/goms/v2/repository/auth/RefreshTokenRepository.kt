package com.goms.v2.repository.auth

import com.goms.v2.domain.auth.RefreshToken

interface RefreshTokenRepository {

    fun save(refreshToken: RefreshToken)
    fun findByIdOrNull(refreshToken: String): RefreshToken?
    fun deleteById(refreshToken: String)

}