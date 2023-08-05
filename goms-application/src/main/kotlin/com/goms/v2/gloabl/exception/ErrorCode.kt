package com.goms.v2.gloabl.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
	val message: String,
	val status: HttpStatus
) {

	// GOMS
	GOMS_SERVER_ERROR("GOMS 서버 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),

	// GAUTH
	GAUTH_SECRET_MISMATCH("클라이언트 시크릿 값이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
	EXPIRED_GAUTH_CODE("만료된 코드입니다.", HttpStatus.UNAUTHORIZED),
	GAUTH_SERVICE_NOT_FOUND("서비스를 찾지 못했습니다.", HttpStatus.NOT_FOUND),

	// EMAIL
	DUPLICATE_EMAIL("중복된 이메일 입니다", HttpStatus.CONFLICT),
	MANY_REQUEST_EMAIL("이메일 요청이 5번을 초과했습니다", HttpStatus.TOO_MANY_REQUESTS),
	MISMATCH_AUTH_KEY("인증번호가 일치하지 않습니다", HttpStatus.BAD_REQUEST),
	EMAIL_NOT_FOUND("존재하지 않은 이메일 입니다", HttpStatus.NOT_FOUND),
	NOT_VERIFY_EMAIL("인증되지 않은 이메일 입니다", HttpStatus.UNAUTHORIZED),
	MAIL_SEND_FAIL("이메일 요청에 실패하셨습니다", HttpStatus.BAD_REQUEST),

	// ACCOUNT
	ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

	// TOKEN
	INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN_TYPE("유효하지 않은 토큰 타입 입니다.", HttpStatus.UNAUTHORIZED),
	EXPIRED_ACCESS_TOKEN("만료된 accessToken 입니다.", HttpStatus.UNAUTHORIZED),
	EXPIRED_REFRESH_TOKEN("만료된 refreshToken 입니다.", HttpStatus.UNAUTHORIZED),

	// OUTING
	BLACKLIST_NOT_ALLOW_OUTING("블랙리스트인 학생은 외출을 할 수 없습니다.", HttpStatus.BAD_REQUEST),
	OUTING_UUID_UNVERIFIED("검증되지 않은 외출 식별자 입니다.", HttpStatus.BAD_REQUEST)

}