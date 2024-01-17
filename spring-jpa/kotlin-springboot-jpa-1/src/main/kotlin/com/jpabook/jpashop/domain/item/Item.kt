package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Category
import com.jpabook.jpashop.domain.exception.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item(
    open var name: String? = null,
    open var price: Int? = null,
    open var stockQuantity: Int? = null,

    @ManyToMany(mappedBy = "items")
    open var categories: MutableList<Category> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "item_id")
    open var id: Long? = null
) {
    /**
     * stock 증가
     */
    fun addStock(quantity: Int) {
        this.stockQuantity = this.stockQuantity!!.plus(quantity)
    }

    /**
     * stock 감소
     */
    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity!!.minus(quantity)
        if (restStock < 0) throw NotEnoughStockException()

        this.stockQuantity = restStock
    }

    fun change(name: String?, price: Int?, stockQuantity: Int?) {
        this.name = name
        this.price = price
        this.stockQuantity = stockQuantity
    }
}
