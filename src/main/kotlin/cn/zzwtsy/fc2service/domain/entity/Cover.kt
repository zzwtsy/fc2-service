package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface Cover : Entity<Cover> {
    companion object : Entity.Factory<Cover>()

    val id: Int
    val fc2VideoBaseInfoFc2Id: Int
    val coverUrl: String
    val fc2VideoBaseInfo: Fc2VideoBaseInfo
}