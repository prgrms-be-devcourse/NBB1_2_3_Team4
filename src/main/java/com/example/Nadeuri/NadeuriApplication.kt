package com.example.Nadeuri

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
object NadeuriApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(NadeuriApplication::class.java, *args)
    }
}
