package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.MoviesDto
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "movies", schema = "fc2_service")
open class Movies {
    @Id
    @Column(name = "fc2Id", nullable = false)
    open var id: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Column(name = "releaseDate")
    open var releaseDate: Instant? = null

    @ManyToMany
    @JoinTable(
        name = "actor_movie",
        joinColumns = [JoinColumn(name = "fc2Id")],
        inverseJoinColumns = [JoinColumn(name = "actorId")]
    )
    open var actors: MutableSet<Actors> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "director_movie",
        joinColumns = [JoinColumn(name = "fc2Id")],
        inverseJoinColumns = [JoinColumn(name = "directorId")]
    )
    open var directors: MutableSet<Directors> = mutableSetOf()

    @OneToMany(mappedBy = "fc2Id")
    open var movieMagnetLinks: MutableSet<MovieMagnetLink> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "movie_tags",
        joinColumns = [JoinColumn(name = "fc2Id")],
        inverseJoinColumns = [JoinColumn(name = "tagId")]
    )
    open var tags: MutableSet<Tags> = mutableSetOf()

    fun toDto(): MoviesDto {
        Actors()
        return MoviesDto(
            id = this.id,
            title = this.title,
            releaseDate = this.releaseDate,
            actors = this.actors.map { it.toDto() }.toMutableSet(),
            directors = this.directors.map { it.toDto() }.toMutableSet(),
            movieMagnetLinks = this.movieMagnetLinks.map { it.toDto() }.toMutableSet(),
            tags = this.tags.map { it.toDto() }.toMutableSet()
        )
    }

}