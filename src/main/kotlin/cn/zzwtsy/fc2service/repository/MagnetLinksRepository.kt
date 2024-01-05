package cn.zzwtsy.fc2service.repository

import cn.zzwtsy.fc2service.model.MagnetLinks
import cn.zzwtsy.fc2service.model.VideoInfo
import org.babyfish.jimmer.spring.repository.KRepository
import org.springframework.stereotype.Repository

@Repository
interface MagnetLinksRepository : KRepository<MagnetLinks, Long> {
    fun saveAll(entities: List<VideoInfo>) {
        sql.entities.saveAll(entities)
    }
}