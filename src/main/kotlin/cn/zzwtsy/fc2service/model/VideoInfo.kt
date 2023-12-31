package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*
import java.time.LocalDate

/**
 * Entity for table "fc2_video_base_info"
 */
@Entity
interface VideoInfo {

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
    val releaseDate: LocalDate

    @OneToOne(mappedBy = "videoInfo")
    val covers: Covers?

    @OneToMany(mappedBy = "videoInfo")
    val magnetLinks: List<MagnetLinks>

    @OneToMany(mappedBy = "videoInfo")
    val previewPictures: List<PreviewPictures>

    @ManyToMany
    @JoinTable(
        name = "video_sellers",
        joinColumnName = "video_id",
        inverseJoinColumnName = "seller_id"
    )
    val sellers: List<Sellers>

    @ManyToMany
    @JoinTable(
        name = "video_tags",
        joinColumnName = "video_id",
        inverseJoinColumnName = "tag_id"
    )
    val tags: List<Tags>
}

