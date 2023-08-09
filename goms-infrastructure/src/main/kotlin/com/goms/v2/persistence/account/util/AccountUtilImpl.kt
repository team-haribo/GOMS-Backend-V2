package com.goms.v2.persistence.account.util

import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.Account
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AccountUtilImpl(
    private val accountRepository: AccountRepository
): AccountUtil {
    override fun getCurrentAccount(): Account {
        val accountIdx = UUID.fromString(SecurityContextHolder.getContext().authentication.name)
        return accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()
    }

}