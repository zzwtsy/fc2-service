package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.model.VideoInfo

interface Fc2VideoInfoParseService {
    fun parse(fc2Id: Long): VideoInfo?
}