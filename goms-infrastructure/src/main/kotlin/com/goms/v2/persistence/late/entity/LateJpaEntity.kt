package com.goms.v2.persistence.late.entity

import com.goms.v2.persistence.BaseIdxEntity
import com.goms.v2.persistence.account.entity.AccountJpaEntity
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "late")
class LateJpaEntity (
    @Column(name = "late_idx")
    override val idx: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx")
    val account: AccountJpaEntity,

    @Column(nullable = false, updatable = false)
    val createdTime: LocalDate = LocalDate.now()
): BaseIdxEntity(idx)