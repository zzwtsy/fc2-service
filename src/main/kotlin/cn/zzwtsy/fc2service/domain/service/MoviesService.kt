package cn.zzwtsy.fc2service.domain.service

import cn.zzwtsy.fc2service.domain.modle.Movies
import cn.zzwtsy.fc2service.domain.repository.MoviesRepository
import cn.zzwtsy.fc2service.dto.MoviesDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoviesService {
    @Autowired
    lateinit var moviesRepository: MoviesRepository
    fun insertAll(entities: MutableIterable<MoviesDto>): MutableList<Movies?> {
        val movies = entities.map { it.toEntity() }
        return moviesRepository.saveAll(movies)
    }

    fun insert(entity: MoviesDto): Movies {
        val movies = entity.toEntity()
        return moviesRepository.save(movies)
    }
}