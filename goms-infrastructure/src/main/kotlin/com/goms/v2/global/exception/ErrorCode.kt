package com.goms.v2.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
	val message: String,
	val status: HttpStatus
) {
}