package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.VideoTag
import org.ktorm.schema.Table
import org.ktorm.schema.int

object VideoTags : Table<VideoTag>("video_tags") {
    val fc2VideoBaseInfoFc2Id = int("fc2_video_base_info_fc2_id").references(Fc2VideoBaseInfos) { it.fc2VideoBaseInfo }
    val tagId = int("tag_id").references(Tags) { it.tag }
}