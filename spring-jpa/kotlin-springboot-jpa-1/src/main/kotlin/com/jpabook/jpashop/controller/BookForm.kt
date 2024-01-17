package com.jpabook.jpashop.controller

class BookForm(
    val id: Long? = null,

    val name: String? = null,
    val price: Int? = null,
    val stockQuantity: Int? = null,

    val author: String? = null,
    val isbn: String? = null
)