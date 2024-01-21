package com.goms.v2.common.util

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.Authentication
import com.goms.v2.domain.auth.exception.AuthenticationNotFoundException
import com.goms.v2.repository.auth.AuthenticationRepository

@UseCaseWithTransaction
class AuthenticationValidator(
    private val authenticationRepository: AuthenticationRepository,
) {

    fun verifyAuthenticationByEmail(email: String): Authentication {
        val authentication = authenticationRepository.findByIdOrNull(email)
            ?: throw AuthenticationNotFoundException()
        if (!authentication.isAuthentication)
            throw AuthenticationNotFoundException()
        return authentication
    }

}