package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.Cover
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Covers : Table<Cover>("covers") {
    val id = int("id").primaryKey().bindTo { it.id }
    val fc2VideoBaseInfoFc2Id = int("fc2_video_base_info_fc2_id").references(Fc2VideoBaseInfos) { it.fc2VideoBaseInfo }
    val coverUrl = varchar("cover_url").bindTo { it.coverUrl }
}