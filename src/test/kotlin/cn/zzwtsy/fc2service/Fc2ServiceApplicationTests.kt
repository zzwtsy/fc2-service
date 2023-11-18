package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.domain.repository.MoviesRepository
import cn.zzwtsy.fc2service.service.RSSService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Lazy

@SpringBootTest
class Fc2ServiceApplicationTests {
    @Autowired
    lateinit var rssService: RSSService

    @Test
    fun contextLoads() {
        rssService.parseRSS()
    }
}
