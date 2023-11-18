package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.DirectorsDto
import jakarta.persistence.*

@Entity
@Table(name = "directors", schema = "fc2_service")
open class Directors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int = -1

    @Column(name = "name", nullable = false)
    open var name: String? = null

    @ManyToMany
    @JoinTable(
        name = "director_movie",
        joinColumns = [JoinColumn(name = "directorId")],
        inverseJoinColumns = [JoinColumn(name = "fc2Id")]
    )
    open var movies: MutableSet<Movies> = mutableSetOf()

    fun toDto(): DirectorsDto {
        return DirectorsDto(
            name = this.name
        )
    }
}