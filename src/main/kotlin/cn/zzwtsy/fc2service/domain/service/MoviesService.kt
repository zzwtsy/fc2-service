package cn.zzwtsy.fc2service.domain.service

import cn.zzwtsy.fc2service.domain.repository.MoviesRepository
import cn.zzwtsy.fc2service.dto.MovieDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoviesService {
    @Autowired
    lateinit var moviesRepository: MoviesRepository

    @Transactional
    fun findAll(): MutableList<MovieDto> {
        return moviesRepository.findAll().map { it.toDto() }.toMutableList()
    }

    fun insert(movie: MovieDto) {
        moviesRepository.save(movie.toEntity())
    }
}