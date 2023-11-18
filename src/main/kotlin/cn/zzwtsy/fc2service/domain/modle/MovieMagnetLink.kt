package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.MovieMagnetLinkDto
import jakarta.persistence.*

@Entity
@Table(name = "movie_magnet_links", schema = "fc2_service")
open class MovieMagnetLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "linkId", nullable = false)
    open var id: Int = -1

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fc2Id")
    open var fc2Id: Movies? = null

    @Column(name = "magnetLink", nullable = false)
    open var magnetLink: String? = null

    fun toDto(): MovieMagnetLinkDto {
        return MovieMagnetLinkDto(
            magnetLink = this.magnetLink
        )
    }
}