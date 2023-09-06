package io.dpopkov.bepomidor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BePomidorApplication

fun main(args: Array<String>) {
	runApplication<BePomidorApplication>(*args)
}
