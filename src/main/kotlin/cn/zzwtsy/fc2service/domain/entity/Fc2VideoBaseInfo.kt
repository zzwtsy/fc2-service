package cn.zzwtsy.fc2service.domain.entity

import org.ktorm.entity.Entity
import java.time.LocalDate

interface Fc2VideoBaseInfo : Entity<Fc2VideoBaseInfo> {
    companion object : Entity.Factory<Fc2VideoBaseInfo>()

    var fc2Id: Int
    var title: String
    var releaseDate: LocalDate
}