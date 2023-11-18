package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Actors
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Actors}
 */
data class ActorsDto(var name: String? = null) : Serializable {
    fun toEntity(): Actors {
        val actors = Actors()
        actors.name = this.name

        return actors
    }
}