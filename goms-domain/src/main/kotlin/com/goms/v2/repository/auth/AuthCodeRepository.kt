package com.goms.v2.repository.auth

import com.goms.v2.domain.auth.AuthCode

interface AuthCodeRepository {

    fun save(authCode: AuthCode)
    fun findByIdOrNull(email: String): AuthCode?

}