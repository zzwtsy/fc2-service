package cn.zzwtsy.fc2service.dto

import java.time.LocalDate

data class Fc2VideoInfoDto(
    val fc2Id: Long,
    val title: String,
    val releaseDate: LocalDate?,
    val cover: String,
    val tags: List<String>,
    val sellers: List<String>,
    val magnetLinks: List<MagnetLinksDto>,
    val previewPictures: List<String>,
)

data class MagnetLinksDto(
    val link: String,
    val fileSize: String,
    val isSubmitterTrusted: Boolean
)