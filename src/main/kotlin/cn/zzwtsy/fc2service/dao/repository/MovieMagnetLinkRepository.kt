package cn.zzwtsy.fc2service.dao.repository;

import cn.zzwtsy.fc2service.dao.entity.MovieMagnetLink
import org.springframework.data.repository.CrudRepository

interface MovieMagnetLinkRepository : CrudRepository<MovieMagnetLink, Int> {
}