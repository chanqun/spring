package com.jpabook.jpashop.api.dto

data class UpdateMemberRequest(
    val name: String
)

data class UpdateMemberResponse(
    val id: Long,
    val name: String
)