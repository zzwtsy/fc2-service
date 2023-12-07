package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.domain.dto.Fc2VideoInfoDto

interface Fc2VideoInfoParseService {
    fun parse(fc2Id: Int): Fc2VideoInfoDto?
}