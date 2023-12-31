package cn.zzwtsy.fc2service.model

import org.babyfish.jimmer.sql.*

/**
 * Entity for table "sellers"
 */
@Entity
interface Sellers {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    val id: Long

    /**
     * 销售者
     */
    val seller: String

    @ManyToMany(mappedBy = "sellers")
    val videoInfo: List<VideoInfo>
}

