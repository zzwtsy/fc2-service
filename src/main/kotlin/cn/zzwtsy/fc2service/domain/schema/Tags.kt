package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.Tag
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Tags : Table<Tag>("tags") {
    val id = int("id").primaryKey().bindTo { it.id }
    val tag = varchar("tag").bindTo { it.tag }
}