package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*

@Entity
@Table(name = "movie_tags", schema = "fc2_service")
open class MovieTag {
    @EmbeddedId
    open var id: MovieTagId? = null

    @MapsId("fc2Id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fc2Id", nullable = false)
    open var fc2Id: Movies? = null

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tagId", nullable = false)
    open var tag: Tags? = null
}