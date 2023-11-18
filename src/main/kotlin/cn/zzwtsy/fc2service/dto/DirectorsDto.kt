package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Directors
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Directors}
 */
data class DirectorsDto(var name: String? = null) : Serializable {
    fun toEntity(): Directors {
        val directors = Directors()
        directors.name = this.name

        return directors
    }
}