package com.goms.v2.repository.email

import com.goms.v2.domain.email.AuthCode

interface AuthCodeRepository {

    fun save(authCode: AuthCode)
    fun findByIdOrNull(email: String): AuthCode?

}