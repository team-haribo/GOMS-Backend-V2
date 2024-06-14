package com.goms.v2.global.security.handler

import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.access.AccessDeniedException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler: AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN)
    }

}