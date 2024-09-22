package com.goms.v2.global.error.response

import com.goms.v2.common.exception.ErrorCode
import com.goms.v2.common.exception.GomsException
import com.goms.v2.global.exception.NullExceptionMessageException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import java.sql.SQLException

data class ErrorResponse(
	val message: String?,
	val status: Int
) {
	companion object {
		fun of(e: GomsException) =
			ErrorResponse(
				message = e.errorCode.message,
				status = e.errorCode.status
			)

		fun of(e: BindingResult): ValidationErrorResponse {
			val fieldErrorMap = e.fieldErrors.associateBy({ it.field }, { it.defaultMessage })

			return ValidationErrorResponse(
				message = fieldErrorMap,
				status = ErrorCode.BAD_REQUEST.status
			)
		}

		fun of(e: DataIntegrityViolationException): DataIntegrityViolationErrorResponse {
			val defaultMessage = "데이터베이스 오류가 발생했습니다. 나중에 다시 시도해 주세요."

			val sqlException = (e.cause as? ConstraintViolationException)?.sqlException ?: e.cause as? SQLException
			val message = sqlException?.message?.let {
				when {
					it.contains("Duplicate entry", ignoreCase = true) -> "중복값이 발생하였습니다."
					else -> defaultMessage
				}
			} ?: throw NullExceptionMessageException()

			return DataIntegrityViolationErrorResponse(
				message = message,
				status = ErrorCode.BAD_REQUEST.status
			)
		}

		fun of(e: HttpMessageNotReadableException) =
			HttpMessageNotReadableErrorResponse(
				message = e.message.toString(),
				status = ErrorCode.BAD_REQUEST.status
			)
	}
}
data class HttpMessageNotReadableErrorResponse(
	val message: String,
	val status: Int
)
data class ValidationErrorResponse(
	val message: Map<String, String?>,
	val status: Int
)
data class DataIntegrityViolationErrorResponse(
	val message: String,
	val status: Int
)