package com.jpabook.jpashop

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
internal class MemberRepositoryTest @Autowired constructor(
    val memberRepository: MemberRepository,
    val testEntityManager: TestEntityManager
) {
    @Test
    //@Rollback(false)
    fun `Member 엔티티 등록 확인`() {
        var member = Member("chanqun")

        var savedMemberId = memberRepository.save(member).id
        var findMember = memberRepository.findById(savedMemberId!!).get()

        // datajpatest는 Transaction 안에서 작동해야하는데
        // @DataJpaTest 는 transaction 이 있음
        assertThat(findMember.id).isEqualTo(member.id)
        assertThat(findMember.name).isEqualTo(member.name)
        assertThat(findMember).isEqualTo(member)
        //같은 영속성 콘텍스트 안에서는 id가 같으면 같은 객체
    }
}
