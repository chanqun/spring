package com.jpabook.jpashop.api.dto

open class Response<T> {

    var data: T? = null

    constructor()
    constructor(data: T) {
        this.data = data
    }

}
