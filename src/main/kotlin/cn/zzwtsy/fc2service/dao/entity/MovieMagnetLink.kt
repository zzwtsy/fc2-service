package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("FC2 视频-磁力链接关联表，存储 FC2 视频和它们对应的磁力链接之间的关系")
@Entity(name = "Movie_Magnet_Link")
@Table(
    name = "movie_magnet_links", schema = "fc2_service", indexes = [
        Index(name = "fc2Id", columnList = "fc2_id")
    ]
)
open class MovieMagnetLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("磁力链接唯一标识ID")
    @Column(name = "link_id", nullable = false)
    open var id: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fc2_id")
    open var movie: Movie? = null

    @Comment("磁力链接")
    @Column(name = "magnet_link", nullable = false)
    open var magnetLink: String? = null
}