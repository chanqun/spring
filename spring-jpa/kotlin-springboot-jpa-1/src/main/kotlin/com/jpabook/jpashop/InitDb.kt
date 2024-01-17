package com.jpabook.jpashop

import com.jpabook.jpashop.domain.*
import com.jpabook.jpashop.domain.item.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class InitDb {
    @Autowired
    lateinit var initService: InitService

    @PostConstruct
    fun init() {
        initService.init()
        initService.init2()
    }

    companion object {
        @Component
        open class InitService {
            @PersistenceContext
            lateinit var em: EntityManager

            @Transactional
            open fun init() {
                val member = Member("userA")
                member.address = Address("서울", "1", "1111")
                em.persist(member)

                val book = Book()
                book.name = "JPA1 BOOK"
                book.price = 10000
                book.stockQuantity = 100
                em.persist(book)

                val book2 = Book()
                book2.name = "JPA2 BOOK"
                book2.price = 20000
                book2.stockQuantity = 100
                em.persist(book2)

                val orderItem1 = OrderItem.createOrderItem(book, 10000, 1)
                val orderItem2 = OrderItem.createOrderItem(book2, 20000, 2)

                val delivery = Delivery()
                delivery.address = member.address

                val createOrder = Order.createOrder(member, delivery, orderItem1, orderItem2)
                em.persist(createOrder)
            }

            @Transactional
            open fun init2() {
                val member = Member("userB")
                member.address = Address("대구", "2", "2222")
                em.persist(member)

                val book = Book()
                book.name = "SPRING1 BOOK"
                book.price = 20000
                book.stockQuantity = 200
                em.persist(book)

                val book2 = Book()
                book2.name = "SPRING2 BOOK"
                book2.price = 40000
                book2.stockQuantity = 300
                em.persist(book2)

                val orderItem1 = OrderItem.createOrderItem(book, 20000, 3)
                val orderItem2 = OrderItem.createOrderItem(book2, 40000, 4)

                val delivery = Delivery()
                delivery.address = member.address

                val createOrder = Order.createOrder(member, delivery, orderItem1, orderItem2)
                em.persist(createOrder)
            }
        }
    }
}