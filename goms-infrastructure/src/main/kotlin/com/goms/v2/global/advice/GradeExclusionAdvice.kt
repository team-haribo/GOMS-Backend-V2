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

@ControllerAdvice
class GradeExclusionAdvice : ResponseBodyAdvice<Any?> {

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

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

    private fun filterGrade(body: Any?): Any? {
        return when (body) {
            is List<*> -> {
                body.filter { item ->
                    when (item) {
                        is LateAccountHttpResponse, is AllAccountHttpResponse, is ProfileHttpResponse -> {
                            val gradeField = item.javaClass.getDeclaredField("grade")
                            gradeField.isAccessible = true
                            val gradeValue = gradeField.get(item) as? Int
                            gradeValue != 6
                        }

                        else -> true
                    }
                }
            }

            is ProfileHttpResponse -> {
                val gradeField = body.javaClass.getDeclaredField("grade")
                gradeField.isAccessible = true
                val gradeValue = gradeField.get(body) as? Int
                if (gradeValue == 6) null else body
            }

            else -> body
        }
    }
}