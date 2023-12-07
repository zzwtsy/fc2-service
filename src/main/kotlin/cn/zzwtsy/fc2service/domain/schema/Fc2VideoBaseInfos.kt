package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.Fc2VideoBaseInfo
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Fc2VideoBaseInfos : Table<Fc2VideoBaseInfo>("fc2_video_base_info") {
    val fc2Id = int("fc2_id").primaryKey().bindTo { it.fc2Id }
    val title = varchar("title").bindTo { it.title }
    val releaseDate = date("release_date").bindTo { it.releaseDate }
}