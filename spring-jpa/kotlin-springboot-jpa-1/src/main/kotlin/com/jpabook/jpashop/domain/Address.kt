package com.jpabook.jpashop.domain

import javax.persistence.Embeddable

@Embeddable
class Address(
    var city: String? = null,
    var street: String? = null,
    var zipcode: String? = null
)
// 자바에서 임베디드 타입은 기본 생성자를 public or protected로 설정 jpa가 프록시나 리플렉션 같은
// 기술을 사용할 수 있도록 지원해야 하기 때문
