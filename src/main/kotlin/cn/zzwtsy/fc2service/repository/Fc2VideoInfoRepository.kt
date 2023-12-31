package cn.zzwtsy.fc2service.repository

import cn.zzwtsy.fc2service.model.VideoInfo
import cn.zzwtsy.fc2service.model.fetchBy
import cn.zzwtsy.fc2service.model.releaseDate
import cn.zzwtsy.fc2service.model.videoId
import cn.zzwtsy.fc2service.utils.Util
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchPage
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.lt
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository

@Repository
interface Fc2VideoInfoRepository : KRepository<VideoInfo, Long> {
    fun queryVideoInfoByOrderByReleaseDateDesc(pageNumber: Int, pageSize: Int): Page<VideoInfo> {
        return sql.createQuery(VideoInfo::class) {
            orderBy(table.releaseDate.desc())
            select(table.fetchBy {
                this.allScalarFields()
                this.covers {
                    coverUrl()
                }
            })
        }.fetchPage(pageNumber, pageSize)
    }

    fun findByVideoId(videoId: Long): VideoInfo? {
        return sql.createQuery(VideoInfo::class) {
            where(table.videoId eq videoId.toLong())
            select(table.fetchBy {
                allScalarFields()
                previewPictures { allScalarFields() }
                magnetLinks { allScalarFields() }
                tags { allScalarFields() }
                sellers { allScalarFields() }
            })
        }.execute().firstOrNull()
    }

    fun queryByVideoIdContains(videoId: List<Long>): List<Long> {
        return sql.createQuery(VideoInfo::class) {
            where(table.videoId valueIn videoId)
            select(table.videoId)
        }.execute()
    }

    fun queryAllByMagnetLinksEmpty(): List<Long> {
        return sql.createQuery(VideoInfo::class) {
            where(table.releaseDate lt Util.getNowDate())
            select(table.videoId)
        }.execute()
    }
}