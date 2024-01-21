package com.goms.v2.domain.auth.data.event

import com.goms.v2.repository.auth.AuthenticationRepository
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {  }

@Component
class CreateAuthenticationEventHandler(
    private val authenticationRepository: AuthenticationRepository
) {

    @EventListener
    fun createAuthentication(createAuthenticationEvent: CreateAuthenticationEvent) {
        log.info("createAuthenticationEvent is active")

        authenticationRepository.save(createAuthenticationEvent.authentication)
    }

}