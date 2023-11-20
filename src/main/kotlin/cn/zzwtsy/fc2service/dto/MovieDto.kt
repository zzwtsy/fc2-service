package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Movie
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.io.Serializable
import java.time.LocalDate

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Movie}
 */
data class MovieDto(
    var id: Int? = null,
    var title: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    var releaseDate: LocalDate? = null,
    var movieMagnetLinks: MutableSet<MovieMagnetLinkDto> = mutableSetOf(),
    var tags: MutableSet<TagDto> = mutableSetOf(),
    var previewPictures: MutableSet<PreviewPictureDto> = mutableSetOf(),
    var sellers: MutableSet<SellerDto> = mutableSetOf()
) : Serializable {
    fun toEntity(): Movie {
        val movie = Movie()
        movie.id = this.id
        movie.title = this.title
        movie.releaseDate = this.releaseDate
        movie.movieMagnetLinks = this.movieMagnetLinks.map { it.toEntity() }.toMutableSet()
        movie.tags = this.tags.map { it.toEntity() }.toMutableSet()
        movie.previewPictures = this.previewPictures.map { it.toEntity() }.toMutableSet()
        movie.sellers = this.sellers.map { it.toEntity() }.toMutableSet()
        return movie
    }
}