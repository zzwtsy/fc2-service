package cn.zzwtsy.fc2service.domain.service

import cn.zzwtsy.fc2service.domain.modle.Movie
import cn.zzwtsy.fc2service.domain.repository.MoviesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoviesService {
    @Autowired
    lateinit var moviesRepository: MoviesRepository

    @Transactional
    fun findAll(): MutableList<Movie> {
        val findAll = moviesRepository.findAll()
        return findAll
    }
}