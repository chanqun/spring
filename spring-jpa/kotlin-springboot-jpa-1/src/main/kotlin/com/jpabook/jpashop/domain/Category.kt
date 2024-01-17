package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class Category(
    var name: String,

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    var items: MutableList<Item> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category,

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "category_id")
    var id: Long
) {
    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}
