package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.property.studentCouncil.OutingUUIDExpTimeProperties
import com.goms.v2.domain.studentCouncil.OutingUUID
import com.goms.v2.repository.studentCouncil.OutingUUIDRepository
import java.util.*

@UseCaseWithTransaction
class CreateOutingUseCase(
    private val outingUUIDRepository: OutingUUIDRepository,
    private val outingUUIDExpTimeProperties: OutingUUIDExpTimeProperties
) {

    fun execute(): UUID {
        val outingUUID = OutingUUID(
            outingUUID = UUID.randomUUID(),
            expiredAt = outingUUIDExpTimeProperties.expiredAt
        )

        return outingUUIDRepository.save(outingUUID)
    }

}