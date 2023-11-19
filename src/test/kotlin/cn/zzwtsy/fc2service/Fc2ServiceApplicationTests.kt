package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.domain.service.MoviesService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Fc2ServiceApplicationTests {
    @Autowired
    lateinit var moviesService: MoviesService

    @Test
    fun contextLoads() {
        val findAll = moviesService.findAll()
    }
}
