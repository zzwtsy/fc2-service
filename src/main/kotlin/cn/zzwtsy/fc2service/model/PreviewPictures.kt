package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "preview_pictures"
 */
@Entity
interface PreviewPictures {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 预览图片URL
     */
    val pictureUrl: String

    @ManyToOne
    val videoInfo: VideoInfo
}

