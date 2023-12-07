package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface PreviewPicture : Entity<PreviewPicture> {
    companion object : Entity.Factory<PreviewPicture>()

    val id: Int
    val fc2VideoBaseInfoFc2Id: Int
    var pictureUrl: String
    var fc2VideoBaseInfo: Fc2VideoBaseInfo
}