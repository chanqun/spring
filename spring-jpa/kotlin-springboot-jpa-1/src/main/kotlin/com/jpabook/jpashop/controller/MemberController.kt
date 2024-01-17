package com.jpabook.jpashop.controller

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model["memberForm"] = MemberForm()
        return "members/createMemberForm"
    }

    @PostMapping("/new")
    fun create(form: MemberForm, result: BindingResult): String {
        // TODO kotlin @Valid 찾아보기
        if (result.hasErrors()) return "members/createMemberForm"

        val address = Address(form.city, form.street, form.zipcode)

        val member = Member(form.name!!, address)

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping
    fun list(model: Model): String {
        // 화면에 뿌릴 때 처리를 해야한다면 반환 객체를 만드는 것이 좋을 것이다.
        // api를 만들때는 절대 멤버 엔티티를 반환하면 안 된다.!!!
        val members = memberService.findMembers()
        model["members"] = members
        return "members/memberList"
    }
}
