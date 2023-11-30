package cn.zzwtsy.fc2service.dao.repository;

import cn.zzwtsy.fc2service.dao.entity.Movie
import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<Movie, Int> {
}