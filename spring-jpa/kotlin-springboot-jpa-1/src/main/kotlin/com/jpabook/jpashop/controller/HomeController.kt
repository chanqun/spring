package com.jpabook.jpashop.controller

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    private val logger = KotlinLogging.logger {}

    @RequestMapping("/")
    fun home(): String {
        logger.info { "home controller " }
        return "home"
    }
}
