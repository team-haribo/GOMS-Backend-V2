package com.goms.v2.global.security.util

import com.goms.v2.common.util.AccountUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AccountUtilImpl: AccountUtil {
    override fun getCurrentAccountIdx(): UUID =
        UUID.fromString(SecurityContextHolder.getContext().authentication.name)

}