package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface VideoTag : Entity<VideoTag> {
    companion object : Entity.Factory<VideoTag>()

    val fc2VideoBaseInfoFc2Id: Int
    val tagId: Int
    var fc2VideoBaseInfo: Fc2VideoBaseInfo
    var tag: Tag
}