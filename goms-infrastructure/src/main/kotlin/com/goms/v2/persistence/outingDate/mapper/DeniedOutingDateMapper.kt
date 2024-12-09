package com.goms.v2.persistence.outing.mapper

import com.goms.v2.domain.outing.DeniedOutingDate
import com.goms.v2.persistence.outingDate.entity.DeniedOutingDateJpaEntity
import org.springframework.stereotype.Component

@Component
class DeniedOutingDateMapper {
	fun toEntity(deniedOutingDate: DeniedOutingDate) =
		DeniedOutingDateJpaEntity(
			idx = deniedOutingDate.idx,
			outingDate = deniedOutingDate.outingDate
		)

	fun toDomain(deniedOutingDateJpaEntity: DeniedOutingDateJpaEntity) =
		DeniedOutingDate(
			idx = deniedOutingDateJpaEntity.idx,
			outingDate = deniedOutingDateJpaEntity.outingDate
		)
}
