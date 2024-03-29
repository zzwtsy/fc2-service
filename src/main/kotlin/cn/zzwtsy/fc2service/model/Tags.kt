package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "tags"
 */
@Entity
interface Tags : BaseEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 标签
     */
    @Key
    val tag: String

    @ManyToMany(mappedBy = "tags")
    val videoInfo: List<VideoInfo>
}
