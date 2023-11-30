package cn.zzwtsy.fc2service.dto

data class MagnetLinkDto(
    val magnetLinks: String,
    val size: String,
    val isSubmitterTrusted: Boolean,
)
