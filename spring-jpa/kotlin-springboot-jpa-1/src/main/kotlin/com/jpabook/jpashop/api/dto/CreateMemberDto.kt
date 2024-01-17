package com.jpabook.jpashop.api.dto

import javax.validation.constraints.NotEmpty

data class CreateMemberRequest(
    @field:NotEmpty
    val name: String
)

data class CreateMemberResponse(
    val id: Long
)
