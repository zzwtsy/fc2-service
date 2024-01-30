package cn.zzwtsy.fc2service.repository

import cn.zzwtsy.fc2service.model.*
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchPage
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.lt
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface Fc2VideoInfoRepository : KRepository<VideoInfo, Long> {
    fun queryVideoInfoListOrderByReleaseDateDesc(pageIndex: Int, pageSize: Int): Page<VideoInfo> {
        return sql.createQuery(VideoInfo::class) {
            orderBy(table.releaseDate.desc())
            select(table.fetchBy {
                this.title()
                this.releaseDate()
                this.covers {
                    coverUrl()
                }
            })
        }.fetchPage(pageIndex, pageSize)
    }

    fun queryVideoInfoListByTagsId(tagId: Long, pageIndex: Int, pageSize: Int): Page<VideoInfo> {
        return sql.createQuery(VideoInfo::class) {
            where(table.tags {
                this.id.eq(tagId)
            })
            orderBy(table.releaseDate.desc())
            select(table.fetchBy {
                this.title()
                this.releaseDate()
                this.covers {
                    coverUrl()
                }
            })
        }.fetchPage(pageIndex, pageSize)
    }

    fun queryVideoInfoByListBySellerId(sellerId: Long, pageIndex: Int, pageSize: Int): Page<VideoInfo> {
        return sql.createQuery(VideoInfo::class) {
            where(table.sellers {
                this.id.eq(sellerId)
            })
            orderBy(table.releaseDate.desc())
            select(table.fetchBy {
                this.title()
                this.releaseDate()
                this.covers {
                    coverUrl()
                }
            })
        }.fetchPage(pageIndex, pageSize)
    }

    fun findByVideoId(videoId: Long): VideoInfo? {
        return sql.createQuery(VideoInfo::class) {
            where(table.videoId eq videoId)
            select(table.fetchBy {
                this.title()
                this.releaseDate()
                covers { this.coverUrl() }
                previewPictures { this.pictureUrl() }
                magnetLinks {
                    this.link()
                    this.fileSize()
                    this.isSubmitterTrusted()
                }
                tags { this.tag() }
                sellers { this.seller() }
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
            where(table.releaseDate lt LocalDate.now().plusDays(-2))
            select(table.fetchBy {
                this.magnetLinks()
            })
        }.execute().filter {
            it.magnetLinks.isEmpty()
        }.map { it.videoId }
    }
}