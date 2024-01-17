package com.jpabook.jpashop.repository

import com.jpabook.jpashop.api.dto.SimpleOrderDto
import com.jpabook.jpashop.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllWithMemberDelivery(): List<Order>

    fun findOrderDtos(): List<SimpleOrderDto>

    fun findAllWithItem(): List<Order>

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order>
}

@Repository
class OrderRepositoryImpl(
    private val entityManager: EntityManager
) {
    //1. string으로 다 만드는 방법
    fun findAll2(orderSearch: OrderSearch): List<Order> {
        return entityManager.createQuery(
            "select o from Order o join o.member m" +
                    " where o.status = :status " +
                    " and m.name like :name", Order::class.java
        ).setParameter("status", orderSearch.orderStatus)
            .setParameter("name", orderSearch.memberName)
            .setMaxResults(1000)
            .resultList
    }

    //2. JPA Criteria

    //3. Query DSL
    fun findAll(orderSearch: OrderSearch) {
        //val order
    }

    fun findAllWithMemberDelivery(): List<Order> {
        return entityManager.createQuery(
            "select o from Order o join fetch o.member join fetch o.delivery d", Order::class.java
        ).resultList
    }

    fun findOrderDtos(): List<SimpleOrderDto> {
        return entityManager.createQuery(
            "select new com.jpabook.jpashop.api.dto.SimpleOrderDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                    " from Order o " +
                    " join o.member m" +
                    " join o.delivery d", SimpleOrderDto::class.java
        ).resultList
    }

    fun findAllWithItem(): List<Order> {
        return entityManager.createQuery(
            "select distinct o from Order o join fetch o.member m join fetch o.delivery d join fetch o.orderItems oi join fetch oi.item i",
            Order::class.java
        ).resultList
    }

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order> {
        return entityManager.createQuery(
            "select distinct o from Order o " +
                    "join fetch o.member m " +
                    "join fetch o.delivery d",
            Order::class.java
        ).setFirstResult(offset).setMaxResults(limit).resultList
    }
}
