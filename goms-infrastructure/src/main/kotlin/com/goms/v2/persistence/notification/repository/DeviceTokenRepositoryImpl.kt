package com.goms.v2.persistence.notification.repository

import com.goms.v2.domain.notification.DeviceToken
import com.goms.v2.persistence.notification.mapper.DeviceTokenMapper
import com.goms.v2.repository.notification.DeviceTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeviceTokenRepositoryImpl(
    private val deviceTokenJpaRepository: DeviceTokenJpaRepository,
    private val deviceTokenMapper: DeviceTokenMapper
): DeviceTokenRepository {

    override fun save(deviceToken: DeviceToken) {
        val deviceTokenEntity = deviceTokenMapper.toEntity(deviceToken)
        deviceTokenJpaRepository.save(deviceTokenEntity)
    }

    override fun findAll(): List<DeviceToken> {
        val deviceTokenEntityList = deviceTokenJpaRepository.findAll().filterNotNull()
        return deviceTokenEntityList.map { deviceTokenMapper.toDomain(it) }
    }

    override fun findByIdxOrNull(accountIdx: UUID): DeviceToken? {
        val deviceTokenEntity = deviceTokenJpaRepository.findByIdOrNull(accountIdx)
        return deviceTokenEntity?.let { deviceTokenMapper.toDomain(it) }
    }

    override fun delete(deviceToken: DeviceToken) {
        deviceTokenJpaRepository.delete(deviceTokenMapper.toEntity(deviceToken))
    }

}