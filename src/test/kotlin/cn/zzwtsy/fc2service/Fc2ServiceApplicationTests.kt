package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.service.GetFc2VideoInfo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@SpringBootTest
class Fc2ServiceApplicationTests {

    @Autowired
    lateinit var getFc2VideoInfo: GetFc2VideoInfo

    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @Test
    fun contextLoads() {
        System.setProperty("http.proxyHost", "127.0.0.1")

        System.setProperty("http.proxyPort", "10809")

        System.setProperty("https.proxyHost", "127.0.0.1")

        System.setProperty("https.proxyPort", "10809")
        val fc2VideoInfo = getFc2VideoInfo.getFc2VideoInfo()
        fc2VideoInfoRepository.saveAll(fc2VideoInfo)
    }
}

fun main() {
    val date = LocalDate.parse("2023-12-09")
    println(date.isAfter(LocalDate.now()))
}