package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Movies
import java.io.Serializable
import java.time.Instant

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Movies}
 */
data class MoviesDto(
    var id: Int? = null,
    var title: String? = null,
    var releaseDate: Instant? = null,
    var actors: MutableSet<ActorsDto> = mutableSetOf(),
    var directors: MutableSet<DirectorsDto> = mutableSetOf(),
    var movieMagnetLinks: MutableSet<MovieMagnetLinkDto> = mutableSetOf(),
    var tags: MutableSet<TagsDto> = mutableSetOf()
) : Serializable {
    fun toEntity(): Movies {
        val movie = Movies()
        movie.id = this.id
        movie.title = this.title
        movie.releaseDate = this.releaseDate
        movie.actors = this.actors.map { it.toEntity() }.toMutableSet()
        movie.directors = this.directors.map { it.toEntity() }.toMutableSet()
        movie.movieMagnetLinks = this.movieMagnetLinks.map { it.toEntity() }.toMutableSet()
        movie.tags = this.tags.map { it.toEntity() }.toMutableSet()

        return movie
    }
}