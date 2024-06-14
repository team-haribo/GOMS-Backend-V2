package com.goms.v2.persistence.account.entity

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
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

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    val grade: Int,

    @Column(nullable = false, length = 10)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val gender: Gender,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val major: Major,

    @Column(nullable = true, columnDefinition = "TEXT")
    val profileUrl: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var authority: Authority,

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    val createdTime: LocalDateTime
): BaseUUIDEntity(idx)
