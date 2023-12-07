package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.dto.MagnetLinksDto
import cn.zzwtsy.fc2service.domain.entity.MagnetLink
import cn.zzwtsy.fc2service.domain.schema.MagnetLinks
import org.ktorm.support.mysql.bulkInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class MagnetLinksDao : BaseDao<MagnetLink, MagnetLinks>(MagnetLinks) {
    @Transactional
    fun insertAll(magnetLinks: Map<MagnetLinksDto, Int>) {
        if (magnetLinks.isEmpty()) return

        magnetLinks.forEach { (magnetLink, fc2Id) ->
            database.bulkInsert(MagnetLinks) {
                item {
                    set(it.link, magnetLink.link)
                    set(it.fc2VideoBaseInfoFc2Id, fc2Id)
                    set(it.fileSize, magnetLink.fileSize)
                    set(it.isSubmitterTrusted, magnetLink.isSubmitterTrusted)
                }
            }
        }
    }
}