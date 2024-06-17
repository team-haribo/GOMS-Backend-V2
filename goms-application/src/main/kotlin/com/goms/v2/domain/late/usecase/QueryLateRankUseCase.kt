package com.goms.v2.domain.late.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.late.data.dto.LateRankDto
import com.goms.v2.repository.late.LateRepository
import org.springframework.cache.annotation.Cacheable

@UseCaseWithReadOnlyTransaction
class QueryLateRankUseCase(
    private val lateRepository: LateRepository,
) {

    @Cacheable(
        value = ["RankList"],
        key = "'rankList'",
        cacheManager = "contentCacheManager",
    )
    fun execute(): List<LateRankDto> {
        val lateRankList = lateRepository.findTop3ByOrderByAccountDesc()
        return lateRankList.map {
            LateRankDto(
                accountIdx = it.account.idx,
                name = it.account.name,
                grade = it.account.grade,
                major = it.account.major,
                gender = it.account.gender,
                profileUrl = it.account.profileUrl
            )
        }
    }

}