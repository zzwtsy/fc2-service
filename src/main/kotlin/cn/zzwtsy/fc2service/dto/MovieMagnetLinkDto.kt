package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.MovieMagnetLink
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.MovieMagnetLink}
 */
data class MovieMagnetLinkDto(var magnetLink: String? = null) : Serializable {
    fun toEntity(): MovieMagnetLink {
        val movieMagnetLink = MovieMagnetLink()
        movieMagnetLink.magnetLink = this.magnetLink

        return movieMagnetLink
    }
}