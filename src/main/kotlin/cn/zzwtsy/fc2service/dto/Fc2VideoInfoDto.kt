package cn.zzwtsy.fc2service.dto

data class Fc2VideoInfoDto(
    val title: String,
    val seller: String,
    val releaseDate: String,
    val cover: String,
    val previewPictures: List<String>,
    val tags: List<String>,
)
