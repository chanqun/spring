package com.jpabook.jpashop.domain

import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class Delivery(
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    var order: Order? = null,

    @Embedded
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus? = null,

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id: Long? = null,
)