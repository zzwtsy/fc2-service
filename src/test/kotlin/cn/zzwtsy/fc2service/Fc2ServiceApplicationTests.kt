package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.domain.service.MoviesService
import cn.zzwtsy.fc2service.dto.MovieDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class Fc2ServiceApplicationTests {
    @Autowired
    lateinit var moviesService: MoviesService

    @Test
    fun contextLoads() {
        val movie = MovieDto(1, "test", LocalDate.now(), mutableSetOf(), mutableSetOf(), mutableSetOf(), mutableSetOf())
        moviesService.insert(movie)
    }
}
