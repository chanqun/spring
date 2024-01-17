package com.jpabook.jpashop.api.dto

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.OrderItem
import com.jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderDto(
    var orderId: Long,
    var name: String,
    var orderDate: LocalDateTime,
    var orderStatus: OrderStatus,
    var address: Address,
    var orderItems: List<OrderItemDto>
)

data class OrderItemDto(
    var name: String,
    var orderPrice: Int,
    var count: Int
) {
    companion object {
        fun of(orderItems: List<OrderItem>): List<OrderItemDto> {
            return orderItems.map {
                OrderItemDto(it.item!!.name!!, it.orderPrice, it.count)
            }
        }
    }
}