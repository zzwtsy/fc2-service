package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.MovieMagnetLinkDto
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity(name = "Movie_Magnet_Link")
@Table(name = "movie_magnet_links")
open class MovieMagnetLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("磁力链接唯一标识ID")
    @Column(name = "link_id", nullable = false)
    open var id: Int? = null

    @MapsId("fc2Id")
    @Comment("FC2 视频ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fc2_id")
    open var fc2Id: Movie? = null

    @Comment("磁力链接")
    @Column(name = "magnet_link", nullable = false)
    open var magnetLink: String? = null

    fun toDto(): MovieMagnetLinkDto {
        return MovieMagnetLinkDto(
            this.magnetLink
        )
    }
}