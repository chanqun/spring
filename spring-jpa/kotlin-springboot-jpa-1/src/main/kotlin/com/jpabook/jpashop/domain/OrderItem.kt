package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
class OrderItem(
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    var orderPrice: Int = 0,
    var count: Int = 0,

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null
) {

    fun cancel() {
        item!!.addStock(count)
    }

    /**
     * 전체 주문 가격 조회
     */
    fun getTotalPrice(): Int {
        return this.orderPrice * this.count
    }

    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            var orderItem = OrderItem()
            orderItem.item = item
            orderItem.orderPrice = orderPrice
            orderItem.count = count

            item.removeStock(count)
            return orderItem
        }
    }
}
