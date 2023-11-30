package cn.zzwtsy.fc2service.dao.service

import cn.zzwtsy.fc2service.dao.entity.Movie
import cn.zzwtsy.fc2service.dao.repository.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MovieService {
    @Autowired
    lateinit var movieRepository: MovieRepository

    @Transactional
    fun insert(movie: Movie) {
        movieRepository.save(movie)
    }
}