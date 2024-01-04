package cn.zzwtsy.fc2service.repository

import cn.zzwtsy.fc2service.model.*
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchPage
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import java.time.LocalDate

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
            where(table.videoId eq videoId)
            select(table.fetchBy {
                allScalarFields()
                previewPictures { allScalarFields() }
                magnetLinks { allScalarFields() }
                tags { allScalarFields() }
                sellers { allScalarFields() }
            })
        }.execute().firstOrNull()
    }

    fun queryVideoIdsExcluding(videoIds: List<Long>): List<Long> {
        val distinct = videoIds.distinct()
        val longList = sql.createQuery(VideoInfo::class) {
            where(table.videoId valueIn distinct)
            select(table.videoId)
        }.execute()

        return if (longList.isEmpty()) {
            distinct
        } else {
            distinct.filterNot { it in longList }
        }
    }

    /**
     * 查询磁力链接为空的视频信息列表
     *
     * @return 磁力链接为空的视频信息ID列表
     */
    fun queryVideoInfoMagnetLinksIsEmpty(): List<Long> {
        return sql.createQuery(VideoInfo::class) {
            where(
                table.videoId valueNotIn subQuery(MagnetLinks::class) {
                    select(table.videoInfoId)
                }
            )
            where(table.releaseDate lt LocalDate.now())
            select(table.videoId)
        }.execute()
    }
}