package com.goms.v2.domain.email.dto.request

import com.fasterxml.jackson.annotation.JsonCreator

data class SendEmailHttpRequest @JsonCreator constructor(
    val email: String
)