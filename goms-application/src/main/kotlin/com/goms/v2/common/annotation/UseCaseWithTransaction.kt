package com.goms.v2.common.annotation

import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Transactional(rollbackFor = [Exception::class])
annotation class UseCaseWithTransaction