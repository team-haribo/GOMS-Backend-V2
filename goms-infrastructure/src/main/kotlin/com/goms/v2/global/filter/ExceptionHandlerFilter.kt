package com.goms.v2.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.ErrorStatus
import com.goms.v2.common.exception.GomsException
import com.goms.v2.global.error.response.ErrorResponse
import com.goms.v2.global.exception.ExpiredTokenException
import com.goms.v2.global.exception.InternalServerException
import com.goms.v2.global.security.jwt.common.exception.InvalidTokenException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import mu.KotlinLogging
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = KotlinLogging.logger {}

class ExceptionHandlerFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { exception ->
            when(exception) {
                is ExpiredJwtException -> {
                    log.warn("ExpiredJwtException Exception Occurred - Message = {} | Status = {}", exception.message, ErrorStatus.UNAUTHORIZED)
                    exceptionToResponse(ErrorResponse.of(ExpiredTokenException()), response)
                }
                is JwtException -> {
                    log.warn("JwtException Exception Occurred - Message = {} | Status = {}", exception.message, ErrorStatus.UNAUTHORIZED)
                    exceptionToResponse(ErrorResponse.of(InvalidTokenException()), response)
                }
                is GomsException -> {
                    log.warn("GomsException Exception Occurred - Message = {} | Status = {}", exception.errorCode.message, exception.errorCode.status)
                    exceptionToResponse(ErrorResponse.of(exception), response)
                }
                else -> {
                    exception.printStackTrace()
                    log.error("Internal Exception Occurred - Message = {} | Status = {}", exception.message, ErrorCode.GOMS_SERVER_ERROR.status)
                    exceptionToResponse(ErrorResponse.of(InternalServerException()), response)
                }
            }
        }
    }

    private fun exceptionToResponse(errorResponse: ErrorResponse, response: HttpServletResponse) {
        response.status = errorResponse.status
        response.contentType = "application/json"
        response.characterEncoding = "utf-8"
        val errorResponse = ErrorResponse(errorResponse.message, errorResponse.status)
        val errorResponseToJson = ObjectMapper().writeValueAsString(errorResponse)
        response.writer.write(errorResponseToJson)
    }

}