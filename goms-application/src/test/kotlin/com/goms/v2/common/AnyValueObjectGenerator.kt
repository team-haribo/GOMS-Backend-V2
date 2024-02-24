package com.goms.v2.common

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.auth.data.event.SaveRefreshTokenEvent
import com.goms.v2.domain.late.data.dto.LateRankDto
import com.goms.v2.domain.notification.Writer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

object AnyValueObjectGenerator {

    inline fun <reified T : Any> anyValueObject(vararg pairs: Pair<String, Any>): T {
        val parameterMap = mutableMapOf(*pairs)
        val constructor = T::class.primaryConstructor!!

        val params = constructor.parameters.map {
            val cls = it.type.classifier as KClass<*>
            if (parameterMap[it.name] != null) parameterMap.remove(it.name)
            else anyValue(cls)
        }
        require(parameterMap.isEmpty())

        return constructor.call(*params.toTypedArray())
    }

    fun anyValue(cls: KClass<*>): Any? =
        when (cls) {
            Boolean::class -> false
            Byte::class -> 0.toByte()
            Short::class -> 0.toShort()
            Char::class -> 0.toChar()
            Int::class -> 0
            Long::class -> 0L
            Float::class -> 0.0F
            Double::class -> 0.0
            String::class -> ""
            Date::class -> Date(0)
            LocalDateTime::class -> LocalDateTime.now()
            LocalTime::class -> LocalTime.now()
            LocalDate::class -> LocalDate.now()
            ZonedDateTime::class -> ZonedDateTime.now()
            UUID::class -> UUID.randomUUID()

            BooleanArray::class -> BooleanArray(0)
            ByteArray::class -> ByteArray(0)
            CharArray::class -> CharArray(0)
            IntArray::class -> IntArray(0)
            LongArray::class -> LongArray(0)
            DoubleArray::class -> DoubleArray(0)

            List::class -> List<Any>(0) {}
            Map::class -> HashMap<Any, Any>()
            Set::class -> HashSet<Any>()
            ArrayList::class -> ArrayList<Any>()
            HashMap::class -> HashMap<Any, Any>()
            HashSet::class -> HashSet<Any>()

            Authority::class -> Authority.ROLE_STUDENT
            Gender::class -> Gender.MAN
            Major::class -> Major.SMART_IOT
            Writer::class -> Writer.GOMS
            LateRankDto::class -> LateRankDto(
                UUID.randomUUID(),
                String(),
                2,
                Major.SMART_IOT,
                Gender.MAN,
                String()
            )
            SaveRefreshTokenEvent::class -> SaveRefreshTokenEvent(
                String(),
                UUID.randomUUID(),
                0
            )

            else -> {
                throw IllegalArgumentException(
                    "Fields of type ${cls.qualifiedName} cannot automatically generate values"
                )
            }
        }

}