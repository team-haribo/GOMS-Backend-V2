package com.goms.v2.persistence.notification.repository

import com.goms.v2.persistence.notification.entity.DeviceTokenEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface DeviceTokenJpaRepository: CrudRepository<DeviceTokenEntity, UUID>