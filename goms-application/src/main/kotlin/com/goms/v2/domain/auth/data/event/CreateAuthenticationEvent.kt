package com.goms.v2.domain.auth.data.event

import com.goms.v2.domain.auth.Authentication

data class CreateAuthenticationEvent(
    val authentication: Authentication
)