package com.goms.v2.repository.email

import com.goms.v2.domain.email.Authentication

interface AuthenticationRepository {

    fun save(authentication: Authentication)
    fun findByIdOrNull(email: String): Authentication
    fun existByEmail(email: String): Boolean

}