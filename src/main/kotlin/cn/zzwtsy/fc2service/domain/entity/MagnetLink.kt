package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface MagnetLink : Entity<MagnetLink> {
    companion object : Entity.Factory<MagnetLink>()

    val id: Int
    val fc2VideoBaseInfoFc2Id: Int
    var link: String
    var fileSize: String
    var isSubmitterTrusted: Boolean
    var fc2VideoBaseInfo: Fc2VideoBaseInfo
}