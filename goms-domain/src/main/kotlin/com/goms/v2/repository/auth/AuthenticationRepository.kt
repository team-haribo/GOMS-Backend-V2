package com.goms.v2.repository.auth

import com.goms.v2.domain.auth.Authentication

interface AuthenticationRepository {

    fun save(authentication: Authentication)
    fun delete(authentication: Authentication)
    fun existByEmail(email: String): Boolean
    fun findByIdOrNull(email: String): Authentication?

}