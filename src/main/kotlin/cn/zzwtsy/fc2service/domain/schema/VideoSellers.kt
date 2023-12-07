package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.VideoSeller
import org.ktorm.schema.Table
import org.ktorm.schema.int

object VideoSellers : Table<VideoSeller>("video_sellers") {
    val fc2VideoBaseInfoFc2Id = int("fc2_video_base_info_fc2_id").references(Fc2VideoBaseInfos) { it.fc2VideoBaseInfo }
    val sellerId = int("seller_id").references(Sellers) { it.seller }
}