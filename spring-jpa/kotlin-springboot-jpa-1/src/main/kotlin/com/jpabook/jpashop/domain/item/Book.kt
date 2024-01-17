package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.controller.BookForm
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book(
    var author: String? = null,
    var isbn: String? = null
) : Item() {
    companion object {
        fun createBook(bookForm: BookForm): Book {
            var book = Book()
            book.name = bookForm.name
            book.author = bookForm.author
            book.isbn = bookForm.isbn
            book.stockQuantity = bookForm.stockQuantity
            book.price = bookForm.price
            book.id = bookForm.id

            return book
        }
    }
}
