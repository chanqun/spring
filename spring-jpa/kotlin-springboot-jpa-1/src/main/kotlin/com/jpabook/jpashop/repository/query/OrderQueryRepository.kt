package com.jpabook.jpashop.repository.query

import com.jpabook.jpashop.api.dto.OrderItemQueryDto
import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.OrderStatus
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityManager

/**
 * 화면과 관련된 쿼리를 따로 관리함
 * 라이프 사이클이 다름
 */
@Repository
class OrderQueryRepository(
    private val em: EntityManager
) {
//    fun findOrderQueryDtos(): List<OrderQueryDto> {
//        val result = findOrders()
//
//        result.forEach {
//            val orderItems: List<OrderItemQueryDto> = findOrderItems(it.orderId)
//            it.orderItems = orderItems
//        }
//
//        return result
//    }

//    fun findAllByDto_optimization(): List<OrderQueryDto> {
//        val result: List<OrderQueryDto> = findOrders()
//
//        val orderIds = result.map {
//            it.orderId
//        }
//
//        val orderItems = em.createQuery(
//            "" +
//                    "select new com.jpabook.jpashop.api.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
//                    " from OrderItem oi" +
//                    " join oi.item i" +
//                    " where oi.order.id in :orderIds", OrderItemQueryDto::class.java
//        ).setParameter("orderIds", orderIds).resultList
//
//        result.forEach {
//            it.orderItems = orderItems
//        }
//
//        return result
//    }

    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return em.createQuery(
            "" +
                    "select new com.jpabook.jpashop.api.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id = :orderId", OrderItemQueryDto::class.java
        )
            .setParameter("orderId", orderId)
            .resultList
    }

//    private fun findOrders(): List<OrderQueryDto> {
//        return em.createQuery(
//            "select new com.jpabook.jpashop.api.dto.OrderQueryDto(m.name, o.id, o.orderDate, o.status, d.address) from Order o " +
//                    "join o.member m " +
//                    "join o.delivery d", OrderQueryDto::class.java
//        ).resultList
//    }

    fun findAllByDto_flat(): List<OrderFlatDto> {
        return em.createQuery(
            "" +
                    "select new" +
                    " com.jpabook.jpashop.repository.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d" +
                    " join o.orderItems oi" +
                    " join oi.item i", OrderFlatDto::class.java
        ).resultList
    }
}

data class OrderFlatDto(
    var orderId: Long,
    var name: String,
    var orderDate: LocalDateTime,
    var orderStatus: OrderStatus,
    var address: Address,
    var itemName: String,
    var orderPrice: Int,
    var count: Int
)