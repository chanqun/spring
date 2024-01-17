package com.jpabook.jpashop.api.dto

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.OrderStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class OrderQueryDto(
    var orderId: Long,
    var name: String,
    var orderDate: LocalDateTime,
    var orderStatus: OrderStatus,
    var address: Address,
    var orderItems: List<OrderItemQueryDto> = mutableListOf()
)

data class OrderItemQueryDto(
    var orderId: Long,
    var itemName: String,
    var orderPrice: Int,
    var count: Int
)