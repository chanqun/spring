package com.jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album(
    var artist: String,
    var etc: String
) : Item()