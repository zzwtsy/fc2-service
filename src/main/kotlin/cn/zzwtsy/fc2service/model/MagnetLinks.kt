package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "magnet_links"
 */
@Entity
interface MagnetLinks {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 磁力链接
     */
    val link: String

    /**
     * 文件大小
     */
    val fileSize: String

    /**
     * 提交者是否可信
     */
    val isSubmitterTrusted: Boolean?

    @ManyToOne
    @Key
    val videoInfo: VideoInfo
}

