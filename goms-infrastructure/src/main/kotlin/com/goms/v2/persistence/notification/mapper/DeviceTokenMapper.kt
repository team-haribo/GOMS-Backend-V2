package com.goms.v2.persistence.notification.mapper

import com.goms.v2.domain.notification.DeviceToken
import com.goms.v2.persistence.notification.entity.DeviceTokenEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
class DeviceTokenMapper {

    fun toEntity(deviceToken: DeviceToken) =
        DeviceTokenEntity(
            accountIdx = deviceToken.accountIdx,
            token = deviceToken.token
        )
    fun toDomain(deviceTokenEntity: DeviceTokenEntity) =
        DeviceToken(
            accountIdx = deviceTokenEntity.accountIdx,
            token = deviceTokenEntity.token
        )

}