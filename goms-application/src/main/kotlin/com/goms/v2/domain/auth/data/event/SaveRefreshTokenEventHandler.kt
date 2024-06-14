package com.goms.v2.domain.auth.data.event

import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.repository.auth.RefreshTokenRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

private val log = KotlinLogging.logger {  }

@Component
class SaveRefreshTokenEventHandler(
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handler(saveRefreshTokenEvent: SaveRefreshTokenEvent) {
        log.info("saveRefreshTokenEvent is activate")

        val refreshToken = RefreshToken(
            refreshToken = saveRefreshTokenEvent.refreshToken,
            accountIdx = saveRefreshTokenEvent.accountIdx,
            expiredAt = saveRefreshTokenEvent.expiredAt
        )

        refreshTokenRepository.save(refreshToken)
    }

}