package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByName(name: String): Member?
}