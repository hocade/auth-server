package com.board.authserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class AuthServerApplication

fun main(args: Array<String>) {
    runApplication<AuthServerApplication>(*args)
}
