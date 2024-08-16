package com.goms.v2.global.error.handler

import com.goms.v2.common.exception.GomsException
import com.goms.v2.global.error.response.DataIntegrityViolationErrorResponse
import com.goms.v2.global.error.response.ErrorResponse
import com.goms.v2.global.error.response.HttpMessageNotReadableErrorResponse
import com.goms.v2.global.error.response.ValidationErrorResponse
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(GomsException::class)
	fun handleGomsException(e: GomsException): ResponseEntity<ErrorResponse> =
		ResponseEntity(ErrorResponse(e.errorCode.message, e.errorCode.status), HttpStatus.valueOf(e.errorCode.status))

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> =
		ResponseEntity(ErrorResponse.of(e), HttpStatus.BAD_REQUEST)

	@ExceptionHandler(HttpMessageNotReadableException::class)
	fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<HttpMessageNotReadableErrorResponse> =
		ResponseEntity(ErrorResponse.of(e), HttpStatus.BAD_REQUEST)


	@ExceptionHandler(DataIntegrityViolationException::class)
	fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ResponseEntity<DataIntegrityViolationErrorResponse> {
		return ResponseEntity(ErrorResponse.of(e), HttpStatus.BAD_REQUEST)
	}

}