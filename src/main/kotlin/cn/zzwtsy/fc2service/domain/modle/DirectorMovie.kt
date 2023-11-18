package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*

@Entity
@Table(name = "director_movie", schema = "fc2_service")
open class DirectorMovie {
    @EmbeddedId
    open var id: DirectorMovieId? = null

    @MapsId("directorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "directorId", nullable = false)
    open var director: Directors? = null

    @MapsId("fc2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fc2Id", nullable = false)
    open var fc2Id: Movies? = null
}