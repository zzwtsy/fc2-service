package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.ActorsDto
import jakarta.persistence.*

@Entity
@Table(name = "actors", schema = "fc2_service")
open class Actors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int = -1

    @Column(name = "name", nullable = false)
    open var name: String? = null

    @ManyToMany
    @JoinTable(
        name = "actor_movie",
        joinColumns = [JoinColumn(name = "actorId")],
        inverseJoinColumns = [JoinColumn(name = "fc2Id")]
    )
    open var movies: MutableSet<Movies> = mutableSetOf()

    fun toDto(): ActorsDto {
        return ActorsDto(
            name = this.name
        )
    }
}