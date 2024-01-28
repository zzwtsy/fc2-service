package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "covers"
 */
@Entity
interface Covers : BaseEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 封面图片URL
     */
    val coverUrl: String

    @OneToOne
    @Key
    val videoInfo: VideoInfo
}

