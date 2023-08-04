package com.goms.v2.persistence.studentCouncil.mapper

import com.goms.v2.domain.studentCouncil.OutingUUID
import com.goms.v2.persistence.studentCouncil.entity.OutingUUIDJpaEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
	componentModel = MappingConstants.ComponentModel.SPRING,
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedSourcePolicy = ReportingPolicy.IGNORE
)
interface OutingUUIDMapper {

	fun toEntity(outingUUID: OutingUUID): OutingUUIDJpaEntity

}