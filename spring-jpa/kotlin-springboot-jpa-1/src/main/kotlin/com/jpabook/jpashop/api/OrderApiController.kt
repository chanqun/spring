package com.jpabook.jpashop.api

import com.jpabook.jpashop.api.dto.OrderDto
import com.jpabook.jpashop.api.dto.OrderItemDto
import com.jpabook.jpashop.api.dto.OrderItemQueryDto
import com.jpabook.jpashop.api.dto.OrderQueryDto
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.repository.OrderRepository
import com.jpabook.jpashop.repository.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors.*


@RestController
class OrderApiController(
    private val orderRepository: OrderRepository,
    private val orderQueryRepository: OrderQueryRepository
) {

    //@GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        //양방향은 꼭 찾아서 json ignore 추가 해주어야한다.
        val orders = orderRepository.findAll()

        orders.forEach {
            it.member!!.name
            it.delivery!!.address
            val orderItems = it.orderItems

            orderItems.forEach { o ->
                o.item!!.name
            }
        }

        return orders
    }

    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrderDto> {
        val orders = orderRepository.findAll()

        return orders.map {
            OrderDto(
                it.id!!,
                it.member!!.name,
                it.orderDate,
                it.status!!,
                it.delivery!!.address!!,
                OrderItemDto.of(it.orderItems)
            )
        }
    }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrderDto> {
        // 1대다에서 다에 맞추기 때문에 데이터를 뻥튀기해서 준다.
        // distinct를 넣어주면 중복이 제거 된다.
        // db에서 가져온 결과는 똑같지만 jpa에서 자체적으로 order가 같은 값이면 중복을 제거해준다.
        //
        // 1대다를 fetch join 하면
        // 단점!!!! - 페이징 불가능 -> 전부 올리고 페이징을 처리한다.;;;;;
        // 컬렉션 페치 조인은 하나만 사용하자
        val orders = orderRepository.findAllWithItem()

        return orders.map {
            OrderDto(
                it.id!!,
                it.member!!.name,
                it.orderDate,
                it.status!!,
                it.delivery!!.address!!,
                OrderItemDto.of(it.orderItems)
            )
        }
    }

    /**
     * ToOne은 페치조인으로 가져오고
     * 컬렉션은 지연 로딩으로 조회한다.
     * hibernate.default_batch_fetch_size, @BatchSize 를 적용한다.
     * // 100 - 1000 개 정도가 좋다.
     */
    @GetMapping("/api/v3.1/orders")
    fun ordersV3_page(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "100") limit: Int,
    ): List<OrderDto> {

        val orders = orderRepository.findAllWithMemberDelivery(offset, limit)

        return orders.map {
            OrderDto(
                it.id!!,
                it.member!!.name,
                it.orderDate,
                it.status!!,
                it.delivery!!.address!!,
                OrderItemDto.of(it.orderItems)
            )
        }
    }

//    @GetMapping("/api/v4/orders")
//    fun ordersV4(): List<OrderQueryDto> {
//        return orderQueryRepository.findOrderQueryDtos()
//    }

//    @GetMapping("/api/v5/orders")
//    fun ordersV5(): List<OrderQueryDto> {
//        return orderQueryRepository.findAllByDto_optimization()
//    }

    @GetMapping("/api/v6/orders")
    fun ordersV6(): List<OrderQueryDto> {
        val flats = orderQueryRepository.findAllByDto_flat()
        // 내가 직접 중복을 걸러내면 된다. 1 : N 으로 된 것을
        // 직접 분해하고 조립
        // -> 쿼리는 한 번이지만 조인으로 인해 v5 보다 느릴 수 있고 페이징 불가능
        // 애플리케이션에서 추가 작업이 크다

        return flats.stream()
            .collect(
                groupingBy(
                    { o ->
                        OrderQueryDto(
                            o.orderId,
                            o.name, o.orderDate, o.orderStatus, o.address
                        )
                    },
                    mapping({ o ->
                        OrderItemQueryDto(
                            o.orderId,
                            o.itemName, o.orderPrice, o.count
                        )
                    }, toList())
                )
            ).entries.stream()
            .map { e ->
                OrderQueryDto(
                    e.key.orderId,
                    e.key.name, e.key.orderDate, e.key.orderStatus,
                    e.key.address, e.value
                )
            }
            .collect(toList())
    }
}
