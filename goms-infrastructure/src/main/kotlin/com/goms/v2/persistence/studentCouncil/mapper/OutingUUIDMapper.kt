package com.goms.v2.persistence.studentCouncil.mapper

import com.goms.v2.domain.studentCouncil.OutingUUID
import com.goms.v2.persistence.studentCouncil.entity.OutingUUIDRedisEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
class OutingUUIDMapper {

    fun toEntity(outingUUID: OutingUUID) =
        OutingUUIDRedisEntity(
            outingUUID = outingUUID.outingUUID,
            expiredAt = outingUUID.expiredAt
        )

}