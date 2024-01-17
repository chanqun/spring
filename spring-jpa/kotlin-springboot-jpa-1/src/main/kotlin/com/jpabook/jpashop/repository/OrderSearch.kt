package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.OrderStatus

class OrderSearch(
    val memberName: String? = "",
    val orderStatus: OrderStatus? = OrderStatus.ORDER
)
