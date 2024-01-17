package com.jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "orders")
class Order(
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery? = null,

    var orderDate: LocalDateTime, // 주문시간

    @Enumerated(EnumType.STRING)
    var status: OrderStatus?, // 주문상태

    @Id @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null
) {

    //연관관계 메서드
    fun addMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun addDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    // 생상 메사드
    companion object {
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            var order = Order(orderDate = LocalDateTime.now(), status = OrderStatus.ORDER)
            order.addMember(member)
            order.addDelivery(delivery)

            for (orderItem in orderItems) {
                order.addOrderItem(orderItem)
            }

            return order
        }
    }

    // 비지니스 로직
    /**
     * 주문 취소
     */
    fun cancel() {
        if (delivery!!.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송 상품은 취소가 불가")
        }

        this.status = OrderStatus.CANCEL

        orderItems.stream().forEach { it.cancel() }
    }

    // 조회 로직
    /**
     * 전체 주문 가격 조회
     */
    fun getTotalPrice(): Int {
        var totalPrice = 0
        orderItems.stream().forEach { totalPrice += it.getTotalPrice() }
        return totalPrice
    }
}
