package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Tag
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Tag}
 */
data class TagDto(var tagName: String? = null) : Serializable {
    fun toEntity(): Tag {
        val tag = Tag()
        tag.tagName = this.tagName
        return tag
    }
}