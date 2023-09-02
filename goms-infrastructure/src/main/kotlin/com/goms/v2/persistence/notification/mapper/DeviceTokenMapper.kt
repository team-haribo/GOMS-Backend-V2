package com.goms.v2.persistence.notification.mapper

import com.goms.v2.domain.notification.DeviceToken
import com.goms.v2.persistence.notification.entity.DeviceTokenEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
interface DeviceTokenMapper {

    fun toEntity(deviceToken: DeviceToken): DeviceTokenEntity
    fun toDomain(deviceTokenEntity: DeviceTokenEntity): DeviceToken

}