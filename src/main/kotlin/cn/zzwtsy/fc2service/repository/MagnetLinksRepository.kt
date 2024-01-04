package cn.zzwtsy.fc2service.repository

import cn.zzwtsy.fc2service.model.MagnetLinks
import cn.zzwtsy.fc2service.model.VideoInfo
import cn.zzwtsy.fc2service.model.by
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.spring.repository.KRepository
import org.springframework.stereotype.Repository

@Repository
interface MagnetLinksRepository : KRepository<MagnetLinks, Long> {
    fun saveAll(entities: Map<Long, List<MagnetLinks>>) {
        val videoInfoList = entities.map { (fc2Id, magnetLinks) ->
            new(VideoInfo::class).by {
                videoId = fc2Id
                this.magnetLinks = magnetLinks
            }
        }
        sql.entities.saveAll(videoInfoList)
    }
}