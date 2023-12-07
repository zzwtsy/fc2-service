package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.entity.VideoTag
import cn.zzwtsy.fc2service.domain.schema.VideoTags
import org.ktorm.support.mysql.bulkInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class VideoTagsDao : BaseDao<VideoTag, VideoTags>(VideoTags) {
    @Transactional
    fun insertAll(tagsId: Map<Int, Int>) {
        if (tagsId.isEmpty()) return

        tagsId.forEach { (tagId, fc2Id) ->
            database.bulkInsert(VideoTags) {
                item {
                    set(it.fc2VideoBaseInfoFc2Id, fc2Id)
                    set(it.tagId, tagId)
                }
            }
        }
    }
}