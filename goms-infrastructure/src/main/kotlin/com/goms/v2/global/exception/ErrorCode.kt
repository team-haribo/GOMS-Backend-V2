package com.goms.v2.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
	val message: String,
	val status: HttpStatus
) {
	// ACCOUNT
	ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	GAUTH_SERVER_ERROR("GAuth 서버 오류 입니다.", HttpStatus.BAD_REQUEST),

	// TOKEN
	INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN_TYPE("유효하지 않은 토큰 타입 입니다.", HttpStatus.UNAUTHORIZED),
	EXPIRED_ACCESS_TOKEN("만료된 accessToken 입니다.", HttpStatus.UNAUTHORIZED),
	EXPIRED_REFRESH_TOKEN("만료된 refreshToken 입니다.", HttpStatus.UNAUTHORIZED),

	// OUTING
	BLACKLIST_NOT_ALLOW_OUTING("블랙리스트인 학생은 외출을 할 수 없습니다.", HttpStatus.BAD_REQUEST),
	OUTING_UUID_UNVERIFIED("검증되지 않은 외출 식별자 입니다.", HttpStatus.BAD_REQUEST),

	// FEIGN
	FEIGN_BAD_REQUEST("FEIGN Bad Request", HttpStatus.BAD_REQUEST),
	FEIGN_FORBIDDEN("FEIGN Forbidden", HttpStatus.FORBIDDEN),
	FEIGN_UNAUTHORIZED("FEIGN Unauthorized", HttpStatus.UNAUTHORIZED)
}