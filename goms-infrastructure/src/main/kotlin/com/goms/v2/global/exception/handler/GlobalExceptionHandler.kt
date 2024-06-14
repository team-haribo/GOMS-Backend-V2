package com.goms.v2.global.exception.handler

import com.goms.v2.common.exception.GomsException
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
	fun handleGomsException(e: GomsException): ResponseEntity<ErrorResponse> =
		ResponseEntity(
			ErrorResponse(e.errorCode.message, e.errorCode.status),
			HttpStatus.valueOf(e.errorCode.status)
		)

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> =
		ResponseEntity(
			ErrorResponse(e.bindingResult.fieldError?.defaultMessage, HttpStatus.BAD_REQUEST.value()),
			HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value())
		)

	@ExceptionHandler(HttpMessageNotReadableException::class)
	fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> =
		ResponseEntity(
			ErrorResponse("json 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST.value()),
			HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value())
		)

}