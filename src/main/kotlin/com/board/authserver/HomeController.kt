package com.board.authserver

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author jinwook.kim
 * @since 3/1/24
 */
@RestController
@RequestMapping(value = ["/"])
class HomeController {


    @GetMapping(value = ["/callback"])
    fun oauth2Callback(@RequestParam code: String?): String? {
        return code
    }

    @GetMapping(value = ["/test"])
    fun test(): String? {
        return "test"
    }
}