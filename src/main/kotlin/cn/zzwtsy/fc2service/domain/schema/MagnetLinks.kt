package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.MagnetLink
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object MagnetLinks : Table<MagnetLink>("magnet_links") {
    val id = int("id").primaryKey().bindTo { it.id }
    val fc2VideoBaseInfoFc2Id = int("fc2_video_base_info_fc2_id").references(Fc2VideoBaseInfos) { it.fc2VideoBaseInfo }
    val link = varchar("link").bindTo { it.link }
    val fileSize = varchar("file_size").bindTo { it.fileSize }
    val isSubmitterTrusted = boolean("is_submitter_trusted").bindTo { it.isSubmitterTrusted }
}