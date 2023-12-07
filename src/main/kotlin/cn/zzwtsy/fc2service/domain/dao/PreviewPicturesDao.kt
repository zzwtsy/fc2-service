package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.entity.PreviewPicture
import cn.zzwtsy.fc2service.domain.schema.PreviewPictures
import org.ktorm.support.mysql.bulkInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PreviewPicturesDao : BaseDao<PreviewPicture, PreviewPictures>(PreviewPictures) {

    @Transactional
    fun insertAll(previewPictures: Map<String, Int>) {
        if (previewPictures.isEmpty()) return

        previewPictures.forEach { (pictureUrl, fc2Id) ->
            database.bulkInsert(PreviewPictures) {
                item {
                    set(it.fc2VideoBaseInfoFc2Id, fc2Id)
                    set(it.pictureUrl, pictureUrl)
                }
            }
        }
    }
}