package com.goms.v2.common.annotation

import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Transactional(readOnly = true, rollbackFor = [Exception::class])
annotation class UseCaseWithReadOnlyTransaction