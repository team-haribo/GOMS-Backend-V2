package com.goms.v2.domain.auth.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.auth.exception.PasswordNotMatchException
import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.late.LateRepository
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithTransaction
class WithdrawUseCase(
    private val accountRepository: AccountRepository,
    private val lateRepository: LateRepository,
    private val outingRepository: OutingRepository,
    private val accountUtil: AccountUtil,
    private val passwordEncoderPort: PasswordEncoderPort
) {

    fun execute(password: String) {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx)
            ?: throw AccountNotFoundException()

        if (!passwordEncoderPort.isPasswordMatch(password, account.password)) {
            throw PasswordNotMatchException()
        }

        lateRepository.deleteAllByAccountIdx(accountIdx)
        outingRepository.deleteAllByAccountIdx(accountIdx)
        accountRepository.delete(account)
    }

}
