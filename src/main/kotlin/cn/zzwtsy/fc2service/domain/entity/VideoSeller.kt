package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity

interface VideoSeller : Entity<VideoSeller> {
    companion object : Entity.Factory<VideoSeller>()

    val fc2VideoBaseInfoFc2Id: Int
    val sellerId: Int
    var fc2VideoBaseInfo: Fc2VideoBaseInfo
    var seller: Seller
}