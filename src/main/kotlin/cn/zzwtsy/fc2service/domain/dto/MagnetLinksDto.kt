package cn.zzwtsy.fc2service.domain.dto

data class MagnetLinksDto(
    val link: String,
    val fileSize: String,
    val isSubmitterTrusted: Boolean
)
