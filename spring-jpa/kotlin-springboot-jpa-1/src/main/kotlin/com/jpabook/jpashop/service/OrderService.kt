package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Delivery
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderItem
import com.jpabook.jpashop.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val orderRepositoryImpl: OrderRepositoryImpl,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository
) {

    //주문
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        //엔티티 조회
        val member = memberRepository.findById(memberId).get()
        val item = itemRepository.findById(itemId).get()

        //배송정보 생성
        val delivery = Delivery()
        delivery.address = member.address!!

        //주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price!!, count)

        val order = Order.createOrder(member, delivery, orderItem)

        orderRepository.save(order) // cascade option 덕분에 order만 저장하면 된다.

        return order.id!!
    }

    // 주문 취소
    @Transactional
    fun cancelOrder(orderId: Long) {
        //주문 엔티티 조회
        val order = orderRepository.findById(orderId).get()
        //주문 취소, -> 더티 체킹하면서 바뀐 것 다 바꿔준다.
        order.cancel()
    }

    //검색
    fun findOrders(orderSearch: OrderSearch): List<Order> {
        return orderRepositoryImpl.findAll2(orderSearch)
    }
}
