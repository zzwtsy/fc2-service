package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Tags
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Tags}
 */
data class TagsDto(var tagName: String? = null) : Serializable {
    fun toEntity(): Tags {
        val tags = Tags()
        tags.tagName = this.tagName

        return tags
    }
}