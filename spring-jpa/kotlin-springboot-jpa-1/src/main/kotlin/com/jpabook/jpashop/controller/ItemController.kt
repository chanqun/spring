package com.jpabook.jpashop.controller

import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/items")
class ItemController(
    private val itemService: ItemService
) {
    @GetMapping("/new")
    fun createFrom(model: Model): String {
        model["form"] = BookForm()
        return "items/createItemForm"
    }

    @PostMapping("/new")
    fun create(form: BookForm): String {
        val book = Book.createBook(form)
        itemService.saveItem(book)

        return "redirect:/items"
    }

    @GetMapping
    fun list(model: Model): String {
        val items = itemService.findItems()
        model["items"] = items
        return "items/itemList"
    }

    @GetMapping("/{itemId}/edit")
    fun updateItemForm(@PathVariable itemId: Long, model: Model): String {
        var item: Book = itemService.findOne(itemId) as Book

        var form = BookForm(item.id, item.name, item.price!!, item.stockQuantity!!, item.author, item.isbn)

        model["form"] = form

        return "/items/updateItemForm"
    }

    @PostMapping("/{itemId}/edit")
    fun updateItem(@ModelAttribute("form") form: BookForm, @PathVariable itemId: Long): String {
        // merge 방법
        // itemService.saveItem(book)
        
        // 값이 많으면 dto를 하나 만들자
        itemService.updateItem(itemId, form)

        return "redirect:/items"
    }
}
