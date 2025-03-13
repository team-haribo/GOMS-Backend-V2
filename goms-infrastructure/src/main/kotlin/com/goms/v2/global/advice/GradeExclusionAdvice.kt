package com.goms.v2.global.advice

import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import com.goms.v2.domain.studentCouncil.dto.response.LateAccountHttpResponse
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * 응답(Response)에서 `grade = 6`인 데이터를 제외하는 `ResponseBodyAdvice`
 *
 * 컨트롤러의 응답 데이터를 가로채고, `LateAccountHttpResponse`, `AllAccountHttpResponse`, `ProfileHttpResponse`
 * 객체에서 `grade` 값이 `6`인 데이터를 제거한다.
 *
 * 6기 학생 데이터를 완전 삭제한다면 해당 클래스는 제거하고, `LateAccountHttpResponse`, `AllAccountHttpResponse`, `ProfileHttpResponse`
 * 객체에서 `grade` 값이 `6`인 데이터를 제거하는 로직을 삭제한다.
 */
@ControllerAdvice
class GradeExclusionAdvice : ResponseBodyAdvice<Any?> {

    /**
     * 모든 ResponseBody에 대해 Advice 적용
     *
     * @param returnType 메서드의 반환 타입 정보
     * @param converterType 사용될 HTTP 메시지 변환기
     * @return 항상 `true`를 반환하여 모든 응답에 적용됨
     */
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    /**
     * 응답 본문에서 `grade = 6`인 데이터를 제외하고 반환
     *
     * @param body 원본 응답 데이터
     * @param returnType 메서드 반환 타입
     * @param selectedContentType 응답의 콘텐츠 타입
     * @param selectedConverterType 사용된 HTTP 메시지 변환기
     * @param request 요청 정보
     * @param response 응답 정보
     * @return 필터링된 응답 데이터
     */
    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        return when (body) {
            is ResponseEntity<*> -> {
                val responseBody = body.body
                val filteredBody = filterGrade(responseBody)
                ResponseEntity.status(body.statusCode)
                    .headers(body.headers)
                    .body(filteredBody)
            }
            else -> filterGrade(body)
        }
    }

    /**
     * 응답 데이터에서 `grade = 6`인 데이터를 필터링
     *
     * - 리스트일 경우, `grade = 6`이 아닌 항목만 남김
     * - 개별 객체일 경우, `grade = 6`이면 `null` 반환
     *
     * @param body 응답 데이터
     * @return 필터링된 응답 데이터 (제외된 경우 `null`)
     */
    private fun filterGrade(body: Any?): Any? {
        return when (body) {
            is List<*> -> body.filterNot { item ->
                item is LateAccountHttpResponse || item is AllAccountHttpResponse || item is ProfileHttpResponse &&
                        getGradeValue(item) == 6
            }
            is ProfileHttpResponse -> if (getGradeValue(body) == 6) null else body
            else -> body
        }
    }

    /**
     * 객체에서 `grade` 필드 값을 가져옴
     *
     * @param obj 대상 객체
     * @return `grade` 값 (없을 경우 `null`)
     */
    private fun getGradeValue(obj: Any): Int? {
        return try {
            val gradeField = obj.javaClass.getDeclaredField("grade")
            gradeField.isAccessible = true
            gradeField.get(obj) as? Int
        } catch (e: NoSuchFieldException) {
            null
        }
    }
}