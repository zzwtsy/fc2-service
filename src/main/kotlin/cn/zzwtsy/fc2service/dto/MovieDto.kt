package cn.zzwtsy.fc2service.dto

import java.io.Serializable
import java.time.Instant

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Movie}
 */
data class MovieDto(var id: Int? = null, var title: String? = null, var releaseDate: Instant? = null) : Serializable