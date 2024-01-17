package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.item.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<Item, Long>