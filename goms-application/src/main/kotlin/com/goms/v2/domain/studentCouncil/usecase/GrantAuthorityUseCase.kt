package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.account.updateAuthority
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.studentCouncil.data.dto.GrantAuthorityDto
import com.goms.v2.repository.account.AccountRepository

@UseCaseWithTransaction
class GrantAuthorityUseCase(
    private val accountRepository: AccountRepository
) {

    fun execute(dto: GrantAuthorityDto) {
        val account = accountRepository.findByIdOrNull(dto.accountIdx)
            ?: throw AccountNotFoundException()
        account.updateAuthority(dto.authority)
        accountRepository.save(account)
    }

}