package cn.zzwtsy.fc2service

import org.babyfish.jimmer.client.EnableImplicitApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableImplicitApi
@EnableScheduling
@EnableAsync
class Fc2ServiceApplication

fun main(args: Array<String>) {
    runApplication<Fc2ServiceApplication>(*args)
}
