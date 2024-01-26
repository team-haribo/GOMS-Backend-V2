package com.goms.v2.common.exception

enum class ErrorCode(
	val message: String,
	val status: Int
) {

	// GOMS
	GOMS_SERVER_ERROR("GOMS 서버 오류 입니다.", ErrorStatus.INTERNAL_SERVER_ERROR),

	// EMAIL
	DUPLICATE_EMAIL("중복된 이메일 입니다.", ErrorStatus.CONFLICT),
	MANY_REQUEST_EMAIL("이메일 요청이 5번을 초과했습니다.", ErrorStatus.TOO_MANY_REQUESTS),
	MISMATCH_AUTH_KEY("인증번호가 일치하지 않습니다.", ErrorStatus.BAD_REQUEST),
	EMAIL_NOT_FOUND("존재하지 않은 이메일 입니다.", ErrorStatus.NOT_FOUND),
	NOT_VERIFY_EMAIL("인증되지 않은 이메일 입니다.", ErrorStatus.UNAUTHORIZED),
	MAIL_SEND_FAIL("이메일 요청에 실패하셨습니다.", ErrorStatus.BAD_REQUEST),

	// AUTH CODE
	AUTH_CODE_NOT_FOUND("인증 코드를 찾을 수 없습니다.", ErrorStatus.NOT_FOUND),
	AUTH_CODE_NOT_MATCH("인증 코드가 일치 하지 않습니다.", ErrorStatus.BAD_REQUEST),
	TOO_MANY_AUTH_CODE_REQUEST("인증 코드 확인 요청을 5번 초과 한 사용자 입니다.", ErrorStatus.TOO_MANY_REQUESTS),

	// AUTH
	ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다.", ErrorStatus.CONFLICT),
	PASSWORD_NOT_MATCH("일치하지 않는 비밀번호입니다.", ErrorStatus.BAD_REQUEST),
	DUPLICATE_NEW_PASSWORD("이미 사용중인 비밀번호입니다.", ErrorStatus.BAD_REQUEST),

	// AUTHENTICATION
	AUTHENTICATION_NOT_FOUND("인증되지 않은 사용자 입니다.", ErrorStatus.UNAUTHORIZED),

	// ACCOUNT
	ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다.", ErrorStatus.NOT_FOUND),

	// TOKEN
	INVALID_TOKEN("유효하지 않은 토큰입니다.", ErrorStatus.UNAUTHORIZED),
	INVALID_TOKEN_TYPE("유효하지 않은 토큰 타입 입니다.", ErrorStatus.UNAUTHORIZED),
	EXPIRED_ACCESS_TOKEN("만료된 accessToken 입니다.", ErrorStatus.UNAUTHORIZED),
	EXPIRED_REFRESH_TOKEN("만료된 refreshToken 입니다.", ErrorStatus.UNAUTHORIZED),

	// OUTING
	BLACKLIST_NOT_ALLOW_OUTING("블랙리스트인 학생은 외출을 할 수 없습니다.", ErrorStatus.BAD_REQUEST),
	OUTING_UUID_UNVERIFIED("검증되지 않은 외출 식별자 입니다.", ErrorStatus.BAD_REQUEST),

	// NOTIFICATION
	DEVICE_TOKEN_NOT_FOUND("device token이 존재하지 않은 사용자 입니다.", ErrorStatus.NOT_FOUND)

}