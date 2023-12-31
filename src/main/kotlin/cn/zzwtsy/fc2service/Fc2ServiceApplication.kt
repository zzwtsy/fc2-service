package cn.zzwtsy.fc2service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableAsync
class Fc2ServiceApplication

fun main(args: Array<String>) {
    runApplication<Fc2ServiceApplication>(*args)
}
