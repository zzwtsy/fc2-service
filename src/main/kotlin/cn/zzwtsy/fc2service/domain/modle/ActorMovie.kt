package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*

@Entity
@Table(name = "actor_movie", schema = "fc2_service")
open class ActorMovie {
    @EmbeddedId
    open var id: ActorMovieId? = null

    @MapsId("actorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "actorId", nullable = false)
    open var actor: Actors? = null

    @MapsId("fc2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fc2Id", nullable = false)
    open var fc2Id: Movies? = null
}