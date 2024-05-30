package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.auth.data.dto.WithdrawDto
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository

@UseCaseWithTransaction
class WithdrawUseCase(
    private val accountRepository: AccountRepository,
    private val accountUtil: AccountUtil,
    private val passwordEncoderPort: PasswordEncoderPort
) {

    fun execute(withdrawDto: WithdrawDto) {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx)
            ?: throw AccountNotFoundException()

        if (!passwordEncoderPort.isPasswordMatch(withdrawDto.password, account.password)) {
            throw PasswordNotMatchException()
        }

        accountRepository.delete(account)
    }

}
