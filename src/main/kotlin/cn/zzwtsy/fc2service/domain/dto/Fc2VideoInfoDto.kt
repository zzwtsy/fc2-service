package cn.zzwtsy.fc2service.domain.dto

import java.time.LocalDate

data class Fc2VideoInfoDto(
    val fc2Id: Int,
    val title: String,
    val releaseDate: LocalDate?,
    val cover: String,
    val tags: List<String>,
    val sellers: List<String>,
    val magnetLinks: List<MagnetLinksDto>,
    val previewPictures: List<String>,
)
