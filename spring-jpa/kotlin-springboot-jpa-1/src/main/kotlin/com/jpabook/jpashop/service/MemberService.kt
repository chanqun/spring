package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
    // mocking 하기도 좋음
) {

//    @Autowired
//    lateinit var memberRepository: MemberRepository

    //회원 가입
    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member) //중복 회원 검증
        memberRepository.save(member)
        return member.id!!
    }

    private fun validateDuplicateMember(member: Member) {
        if (memberRepository.findByName(member.name) != null) throw IllegalStateException("이미 존재하는 회원")
        // WAS가 여러개 뜨는데 동시에 DB insert하면 문제가 될 수 있음 !!
        // Unique 제약 조건으로 잡는 것을 권장
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findOne(id: Long): Member {
        return memberRepository.findById(id).get()
    }

    @Transactional
    fun update(id: Long, name: String) {
        val member = memberRepository.findById(id).get()

        member.name = name
    }
}
