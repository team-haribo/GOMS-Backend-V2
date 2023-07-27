package com.goms.v2.persistence.account.entity

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class StudentNumberEntity(
    @Column(name = "grade", nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(name = "number", nullable = false)
    val number: Int
)