package com.goms.v2.common.error

abstract class GomsException (
    val errorProperty: ErrorProperty
): RuntimeException() {
    override fun fillInStackTrace(): Throwable = this
}