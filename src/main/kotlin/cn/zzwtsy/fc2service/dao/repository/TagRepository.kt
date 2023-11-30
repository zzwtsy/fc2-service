package cn.zzwtsy.fc2service.dao.repository;

import cn.zzwtsy.fc2service.dao.entity.Tag
import org.springframework.data.repository.CrudRepository

interface TagRepository : CrudRepository<Tag, Int> {
}