package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.PreviewPicture
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.PreviewPicture}
 */
data class PreviewPictureDto(var previewPicture: String? = null) : Serializable {
    fun toEntity(): PreviewPicture {
        val previewPicture = PreviewPicture()
        previewPicture.previewPicture = this.previewPicture
        return previewPicture
    }
}