package com.goms.v2.persistence.account.entity

import com.goms.v2.domain.account.Authority
import com.goms.v2.persistence.BaseUUIDEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account")
class AccountJpaEntity(
    @Column(name = "account_idx")
    override val idx: UUID,

    @Column(nullable = false, length = 40)
    val email: String,

    @Embedded
    val studentNumber: StudentNumberEntity,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = true, columnDefinition = "TEXT")
    val profileUrl: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var authority: Authority,

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    val createdTime: LocalDateTime
): BaseUUIDEntity(idx)
