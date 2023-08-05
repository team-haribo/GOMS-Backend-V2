package com.goms.v2.repository.email

import com.goms.v2.domain.email.EmailAuth

interface EmailAuthRepository {

    fun save(emailAuth: EmailAuth)
    fun findByIdOrNull(email: String): EmailAuth?
    fun existByEmail(email: String): Boolean

}