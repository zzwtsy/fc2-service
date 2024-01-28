package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*
import java.time.LocalDate

/**
 * Entity for table "fc2_video_base_info"
 */
@Entity
interface VideoInfo : BaseEntity {

    /**
     * FC2 ID
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val videoId: Long

    /**
     * 视频标题
     */
    val title: String

    /**
     * 发布日期
     */
    val releaseDate: LocalDate?

    @OneToOne(mappedBy = "videoInfo")
    val covers: Covers?

    @ManyToMany
    @JoinTable(
        name = "video_info_magnet_links_mapping",
        joinColumnName = "video_id",
        inverseJoinColumnName = "magnet_link_id"
    )
    val magnetLinks: List<MagnetLinks>

    @ManyToMany
    @JoinTable(
        name = "video_info_preview_pictures_mapping",
        joinColumnName = "video_id",
        inverseJoinColumnName = "picture_id"
    )
    val previewPictures: List<PreviewPictures>

    @ManyToMany
    @JoinTable(
        name = "video_info_sellers_mapping",
        joinColumnName = "video_id",
        inverseJoinColumnName = "seller_id"
    )
    val sellers: List<Sellers>

    @ManyToMany
    @JoinTable(
        name = "video_info_tags_mapping",
        joinColumnName = "video_id",
        inverseJoinColumnName = "tag_id"
    )
    val tags: List<Tags>
}

