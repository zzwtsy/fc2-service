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
    @Key
    val pictureUrl: String

    @ManyToMany(mappedBy = "previewPictures")
    val videoInfo: List<VideoInfo>
}

