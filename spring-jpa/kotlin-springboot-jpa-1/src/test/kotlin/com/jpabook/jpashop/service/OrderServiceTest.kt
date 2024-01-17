package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.domain.OrderStatus
import com.jpabook.jpashop.domain.exception.NotEnoughStockException
import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.domain.item.Item
import com.jpabook.jpashop.repository.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
internal class OrderServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val orderService: OrderService,
    private val orderRepository: OrderRepository
) {

    @Test
    fun `상품 주문`() {
        val member = createMember()

        val book = createBook("나", 10000, 10)

        val orderCount = 2

        val orderId = orderService.order(member.id!!, book.id!!, orderCount)

        val getOrder = orderRepository.findById(orderId).get()

        assertThat(getOrder.status == OrderStatus.ORDER).isTrue
        assertThat(getOrder.orderItems.size).isEqualTo(1)
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount)
        assertThat(book.stockQuantity).isEqualTo(8)
    }

    @Test
    fun `상품주문 재고수량 초과`() {
        val member = createMember()

        val book = createBook("나", 10000, 10)

        val orderCount = 11

        assertThatThrownBy { orderService.order(member.id!!, book.id!!, orderCount) }
            .isInstanceOf(NotEnoughStockException::class.java)
        //사실 remove stock 단위 테스트가 필요함
    }

    @Test
    fun `주문 취소`() {
        val member = createMember()
        val book = createBook("나", 10000, 10)

        val orderCount = 2

        val orderId = orderService.order(member.id!!, book.id!!, orderCount)

        orderService.cancelOrder(orderId)

        val getOrder = orderRepository.findById(orderId).get()

        assertThat(getOrder.status == OrderStatus.CANCEL).isTrue
        assertThat(book.stockQuantity).isEqualTo(10)
    }

    private fun createBook(author: String, price: Int, stockQuantity: Int): Book {
        val book = Book(author, "123")
        book.price = price
        book.stockQuantity = stockQuantity
        entityManager.persist(book)
        return book
    }

    private fun createMember(): Member {
        val address = Address("서울", "선유로", "12")
        val member = Member("chanqun", address)
        entityManager.persist(member)
        return member
    }

}
