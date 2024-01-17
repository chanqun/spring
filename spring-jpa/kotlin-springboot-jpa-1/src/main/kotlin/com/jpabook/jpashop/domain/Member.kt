package com.jpabook.jpashop.domain

import javax.persistence.*

@Entity
class Member(
    var name: String,

    @Embedded
    var address: Address? = null,

    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null
)
