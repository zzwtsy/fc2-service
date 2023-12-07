package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface Tag : Entity<Tag> {
    companion object : Entity.Factory<Tag>()

    val id: Int
    var tag: String
}