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
    /**
     * 根据发布日期倒序查询视频信息列表
     *
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     * @return 视频信息列表
     */
    fun queryVideoInfoListOrderByReleaseDateDesc(pageIndex: Int, pageSize: Int): Page<VideoInfo> {
        // 执行SQL查询，获取视频信息列表
        val list = sql.createQuery(VideoInfo::class) {
            orderBy(table.releaseDate.desc())
            select(table.fetchBy {
                this.title()
                this.releaseDate()
                this.covers {
                    coverUrl()
                }
                this.magnetLinks()
            })
        }.fetchPage(pageIndex, pageSize)

        // 返回视频信息列表
        return list
    }

    /**
     * 根据标签ID查询视频信息列表
     *
     * @param tagId 标签ID
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     * @return 视频信息列表
     */
    fun queryVideoInfoListByTagsId(tagId: Long, pageIndex: Int, pageSize: Int): Page<VideoInfo> {
        // 执行SQL查询，获取视频信息列表
        val list = sql.createQuery(VideoInfo::class) {
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
                this.magnetLinks()
            })
        }.fetchPage(pageIndex, pageSize)

        // 返回视频信息列表
        return list
    }


    /**
     * 根据卖家ID查询视频信息列表
     *
     * @param sellerId 卖家ID
     * @param pageIndex 页码
     * @param pageSize 每页数量
     * @return 查询结果的分页对象
     */
    fun queryVideoInfoByListBySellerId(
        sellerId: Long,
        pageIndex: Int,
        pageSize: Int
    ): Page<VideoInfo> {
        return sql.createQuery(VideoInfo::class) {
            where(table.sellers { this.id eq sellerId })
            orderBy(table.releaseDate.desc())
            select(table.fetchBy {
                this.title()
                this.releaseDate()
                this.covers { coverUrl() }
                this.magnetLinks()
            })
        }.fetchPage(pageIndex, pageSize)
    }

    /**
     * 根据视频ID查找视频信息
     *
     * @param videoId 视频ID
     * @return 查询到的视频信息对象，如果未找到则返回null
     */
    fun findVideoInfoByVideoId(videoId: Long): VideoInfo? {
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


    /**
     * 查询需要排除的视频ID列表
     *
     * @param videoIds 需要排除的视频ID列表
     * @return 排除后的视频ID列表
     */
    fun queryVideoIdsExcluding(videoIds: List<Long>): List<Long> {
        // 获取去重后的视频ID列表
        val distinct = videoIds.distinct()

        // 执行SQL查询，获取视频ID列表
        val longList = sql.createQuery(VideoInfo::class) {
            where(table.videoId valueIn distinct)
            select(table.videoId)
        }.execute()

        // 如果查询结果为空，返回去重后的视频ID列表
        // 否则返回去重后不在查询结果中的视频ID列表
        return if (longList.isEmpty()) {
            distinct
        } else {
            distinct.filterNot { it in longList }
        }
    }

    /**
     * 查询视频信息磁力链接为空的视频ID列表
     *
     * @return 磁力链接为空的视频ID列表
     */
    fun queryVideoInfoMagnetLinksIsEmpty(): List<Long> {
        // 执行SQL查询，获取视频信息列表
        val list = sql.createQuery(VideoInfo::class) {
            where(table.releaseDate lt LocalDate.now().plusDays(-2))
            select(table.fetchBy {
                this.magnetLinks()
            })
        }.execute()

        // 过滤磁力链接为空的视频信息，并返回视频ID列表
        return list.filter {
            it.magnetLinks.isEmpty()
        }.map { it.videoId }
    }

}