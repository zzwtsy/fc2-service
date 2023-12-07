package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.entity.VideoSeller
import cn.zzwtsy.fc2service.domain.schema.VideoSellers
import org.ktorm.support.mysql.bulkInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class VideoSellersDao : BaseDao<VideoSeller, VideoSellers>(VideoSellers) {
    @Transactional
    fun insertAll(sellerId: Map<Int, Int>) {
        if (sellerId.isEmpty()) return

        sellerId.forEach { (sellerId, fc2Id) ->
            database.bulkInsert(VideoSellers) {
                item {
                    set(it.sellerId, sellerId)
                    set(it.fc2VideoBaseInfoFc2Id, fc2Id)
                }
            }
        }
    }
}