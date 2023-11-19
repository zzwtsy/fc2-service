package cn.zzwtsy.fc2service.domain.repository

import cn.zzwtsy.fc2service.domain.modle.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MoviesRepository : JpaRepository<Movie, Int> {
}