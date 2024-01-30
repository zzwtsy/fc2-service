package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "sellers"
 */
@Entity
interface Sellers : BaseEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 销售者
     */
    @Key
    val seller: String

    @Key
    val sellerWebSiteId: String?

    @ManyToMany(mappedBy = "sellers")
    val videoInfo: List<VideoInfo>
}

