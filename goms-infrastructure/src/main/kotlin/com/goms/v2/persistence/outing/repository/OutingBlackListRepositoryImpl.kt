package com.goms.v2.persistence.outing.repository

import com.goms.v2.domain.outing.OutingBlackList
import com.goms.v2.persistence.outing.mapper.OutingBlackListMapper
import com.goms.v2.repository.outing.OutingBlackListRepository
import org.springframework.stereotype.Repository

@Repository
class OutingBlackListRepositoryImpl(
    private val outingBlacklistJpaRepository: OutingBlackListJpaRepository,
    private val outingBlacklistMapper: OutingBlackListMapper
): OutingBlackListRepository {

    override fun save(outingBlacklist: OutingBlackList) {
        val outingBlacklistEntity = outingBlacklistMapper.toEntity(outingBlacklist)
        outingBlacklistJpaRepository.save(outingBlacklistEntity)
    }

}