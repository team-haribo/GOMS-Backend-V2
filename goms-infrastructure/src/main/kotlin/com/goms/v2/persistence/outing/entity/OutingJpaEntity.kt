package com.goms.v2.persistence.outing.entity

import com.goms.v2.persistence.BaseIdxEntity
import com.goms.v2.persistence.account.entity.AccountJpaEntity
import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "outing")
class OutingJpaEntity(
    @Column(name = "outing_idx")
    override val idx: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx")
    val account: AccountJpaEntity,

    @Column(nullable = false, updatable = false)
    val createdTime: LocalTime = LocalTime.now()
):BaseIdxEntity(idx)