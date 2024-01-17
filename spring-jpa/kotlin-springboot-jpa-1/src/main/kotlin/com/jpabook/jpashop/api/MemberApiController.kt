package com.jpabook.jpashop.api

import com.jpabook.jpashop.api.dto.*
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.service.MemberService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class MemberApiController(
    private val memberService: MemberService
) {

    @GetMapping("/api/v1/members")
    fun membersV1(): List<Member> {
        //entity에 @JsonIgnore을 넣으면 order 정보가 빠진다.
        return memberService.findMembers()
    }

    /**
     * "count" : 4
     * "list" : array만 반환하면 하면 유연성이 떨어짐
     */
    @GetMapping("/api/v2/members")
    fun membersV2(): Response<List<MemberDto>> {
        val members = memberService.findMembers()

        val collect = members.map {
            MemberDto(it.name)
        }

        return Response(collect)
    }

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        //entity에 valid를 걸어 놓으면 spec이 변경되거나 할 때 변경 될 부분이 많다.
        //api spec이 변경 될 수 있음
        //entity가 변한다고 api spec이 변경 된다는 것은 말도 안돼 -> 따로 DTO를 만들자
        val id = memberService.join(member)

        return CreateMemberResponse(id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid req: CreateMemberRequest): CreateMemberResponse {
        val member = Member(req.name)

        val id = memberService.join(member)

        return CreateMemberResponse(id)
    }

    @PutMapping("/api/v2/members/{id}")
    fun updateMember(@PathVariable id: Long, @RequestBody @Valid req: UpdateMemberRequest): UpdateMemberResponse {
        memberService.update(id, req.name)

        val findMember = memberService.findOne(id)

        return UpdateMemberResponse(findMember.id!!, findMember.name)
    }


}
