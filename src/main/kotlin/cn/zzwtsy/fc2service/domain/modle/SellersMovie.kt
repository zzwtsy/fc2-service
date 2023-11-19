package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("导演- FC2 视频关联表，存储导演和他们执导的 FC2 视频之间的关系")
@Entity(name = "Sellers_Movie")
@Table(
    name = "sellers_movie", schema = "fc2_service", indexes = [
        Index(name = "fc2Id", columnList = "fc2_id")
    ]
)
open class SellersMovie {
    @EmbeddedId
    open var id: SellersMovieId? = null

    @MapsId("sellerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("导演ID")
    @JoinColumn(name = "seller_id", nullable = false)
    open var sellerId: Seller? = null

    @MapsId("fc2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("FC2 视频ID")
    @JoinColumn(name = "fc2_id", nullable = false)
    open var fc2Id: Movie? = null
}