package com.goms.v2.persistence.outingDate.entity

import com.goms.v2.persistence.BaseIdxEntity
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "danied_outing_date")
class DeniedOutingDateJpaEntity(
    @Column(name = "denied_outing_date_idx")
    override val idx: Long,

    @Column(nullable = false, updatable = false)
    val outingDate: LocalDate = LocalDate.now()
) : BaseIdxEntity(idx)