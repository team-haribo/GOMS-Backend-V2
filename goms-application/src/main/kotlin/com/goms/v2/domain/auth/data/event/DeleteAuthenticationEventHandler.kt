package com.goms.v2.domain.auth.data.event

import com.goms.v2.repository.auth.AuthenticationRepository
import mu.KotlinLogging
import org.springframework.context.event.EventListener

private val log = KotlinLogging.logger {}

class DeleteAuthenticationEventHandler(
    private val authenticationRepository: AuthenticationRepository
) {

    @EventListener
    fun deleteAuthentication(createAuthenticationEvent: CreateAuthenticationEvent) {
        log.info("deleteAuthenticationEvent is active")

        authenticationRepository.delete(createAuthenticationEvent.authentication)
    }

}