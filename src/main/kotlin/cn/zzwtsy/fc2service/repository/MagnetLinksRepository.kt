package cn.zzwtsy.fc2service.repository

import cn.zzwtsy.fc2service.model.MagnetLinks
import cn.zzwtsy.fc2service.model.by
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.spring.repository.KRepository
import org.springframework.stereotype.Repository

@Repository
interface MagnetLinksRepository : KRepository<MagnetLinks, Long> {
    fun saveByVideoInfoId(entity: Map<Long, List<MagnetLinks>>) {
        for ((fc2Id, magnetLinksDtos) in entity) {
            magnetLinksDtos.forEach {
                val magnetLinks = new(MagnetLinks::class).by {
                    link = it.link
                    fileSize = it.fileSize
                    isSubmitterTrusted = it.isSubmitterTrusted
                }
                sql.save(magnetLinks)
            }
        }
    }
}