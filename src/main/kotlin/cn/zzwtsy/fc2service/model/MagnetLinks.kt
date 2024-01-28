package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "magnet_links"
 */
@Entity
interface MagnetLinks : BaseEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 磁力链接
     */
    @Key
    val link: String

    /**
     * 文件大小
     */
    val fileSize: String

    /**
     * 提交者是否可信
     */
    val isSubmitterTrusted: Boolean?

    @ManyToMany(mappedBy = "magnetLinks")
    val videoInfo: List<VideoInfo>
}

