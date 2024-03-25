package com.goms.v2.global.filter

import mu.KotlinLogging
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = KotlinLogging.logger {  }

class RequestLogFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("client ip = ${request.remoteAddr}")
        log.info("request method = ${request.method}")
        log.info("request url = ${request.requestURI}")

        try {
            filterChain.doFilter(request, response)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        log.info("response status = ${response.status}")
    }

}