package com.goms.v2.global.security.principal

import com.goms.v2.global.security.jwt.common.exception.InvalidTokenException
import com.goms.v2.repository.account.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudentCouncilDetailsService(
    private val accountRepository: AccountRepository
): UserDetailsService {

    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    override fun loadUserByUsername(username: String?): UserDetails =
        accountRepository.findByIdOrNull(UUID.fromString(username))
            .let { it ?: throw InvalidTokenException() }
            .let { StudentCouncilDetails(it.idx) }

}