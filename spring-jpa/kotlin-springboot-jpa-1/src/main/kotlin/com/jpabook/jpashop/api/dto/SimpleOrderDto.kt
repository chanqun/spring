package com.jpabook.jpashop.api.dto

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class SimpleOrderDto(
    val orderId: Long? = null,
    val name: String? = null,
    val orderDate: LocalDateTime? = null,
    val orderStatus: OrderStatus? = null,
    val address: Address? = null
)
