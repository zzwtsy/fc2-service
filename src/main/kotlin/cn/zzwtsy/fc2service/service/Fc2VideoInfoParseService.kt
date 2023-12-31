package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.dto.Fc2VideoInfoDto

interface Fc2VideoInfoParseService {
    fun parse(fc2Id: Long): Fc2VideoInfoDto?
}