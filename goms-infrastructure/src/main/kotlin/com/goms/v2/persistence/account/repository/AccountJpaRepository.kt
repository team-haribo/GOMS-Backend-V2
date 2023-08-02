package com.goms.v2.persistence.account.repository

import com.goms.v2.persistence.account.entity.AccountJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AccountJpaRepository: CrudRepository<AccountJpaEntity, UUID> {

    fun findByEmail(email: String): AccountJpaEntity?

}
