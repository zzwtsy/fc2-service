package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface Seller : Entity<Seller> {
    companion object : Entity.Factory<Seller>()

    val id: Int
    var seller: String
}