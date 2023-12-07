package cn.zzwtsy.fc2service.domain.schema

import cn.zzwtsy.fc2service.domain.entity.Seller
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Sellers : Table<Seller>("sellers") {
    val id = int("id").primaryKey().bindTo { it.id }
    val seller = varchar("seller").bindTo { it.seller }
}