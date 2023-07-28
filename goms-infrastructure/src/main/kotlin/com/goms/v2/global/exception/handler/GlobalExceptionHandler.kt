package com.goms.v2.global.exception.handler

import com.goms.v2.global.exception.exception.GomsException
import com.goms.v2.global.exception.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(GomsException::class)
	fun handler(e: GomsException): ResponseEntity<ErrorResponse> =
		ResponseEntity(
			ErrorResponse(e.errorCode.message, e.errorCode.status.value()),
			HttpStatus.valueOf(e.errorCode.status.name)
		)

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun  handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> =
		ResponseEntity(
			ErrorResponse(e.message, e.hashCode()),
			HttpStatus.valueOf(e.hashCode())
		)

}