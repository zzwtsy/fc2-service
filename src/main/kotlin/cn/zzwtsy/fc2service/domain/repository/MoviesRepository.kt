package cn.zzwtsy.fc2service.domain.repository

import cn.zzwtsy.fc2service.domain.modle.Movies
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface MoviesRepository : JpaRepository<Movies, Int> {
    @EntityGraph(attributePaths = ["actors", "tags"])
    override fun findAll(): MutableList<Movies>
}