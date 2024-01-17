package com.jpabook.jpashop.controller

import com.jpabook.jpashop.domain.OrderStatus
import com.jpabook.jpashop.repository.OrderSearch
import com.jpabook.jpashop.service.ItemService
import com.jpabook.jpashop.service.MemberService
import com.jpabook.jpashop.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
class OrderController @Autowired constructor(
    private val orderService: OrderService,
    private val memberService: MemberService,
    private val itemService: ItemService
) {

    @GetMapping("/order")
    fun createForm(model: Model): String {
        val members = memberService.findMembers()
        val items = itemService.findItems()

        model["members"] = members
        model["items"] = items

        return "order/orderForm"
    }

    @PostMapping("/order")
    fun order(
        @RequestParam("memberId") memberId: Long,
        @RequestParam("itemId") itemId: Long,
        @RequestParam("count") count: Int
    ): String {
        orderService.order(memberId, itemId, count)
        return "redirect:/orders"
    }

    @GetMapping("/orders")
    fun orderList(@ModelAttribute("orderSearch") orderSearch: OrderSearch, model: Model): String {
        val orders = orderService.findOrders(orderSearch)

        model["orders"] = orders
        model["status"] = OrderStatus.values()
        return "order/orderList"
    }

    @PostMapping("/orders/{orderId}/cancel")
    fun cancelOrder(@PathVariable("orderId") orderId: Long): String {
        orderService.cancelOrder(orderId)
        return "redirect:/orders"
    }
}