package cn.zzwtsy.fc2service.task

import cn.zzwtsy.fc2service.dto.MagnetLinksDto
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.repository.MagnetLinksRepository
import cn.zzwtsy.fc2service.service.GetFc2VideoInfo
import cn.zzwtsy.fc2service.service.SukebeiNyaaHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Fc2VideoTask {
    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var getFc2VideoInfo: GetFc2VideoInfo

    @Autowired
    private lateinit var magnetLinksRepository: MagnetLinksRepository

    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @Autowired
    private lateinit var sukebeiNyaaHTMLParseService: SukebeiNyaaHTMLParseService

    // 每间隔 8 小时执行一次
    // @Scheduled(fixedDelay = 8, timeUnit = TimeUnit.HOURS)
    fun executeGetNewVideoInfoTask() {
        getFc2VideoInfo.getFc2VideoInfo()
    }

    fun executeGetVideoMagnetLinksTask() {
        val magnetLinksEmpty = fc2VideoInfoRepository.queryAllByMagnetLinksEmpty()
        val magnetLinks = mutableMapOf<Long, List<MagnetLinksDto>>()
        magnetLinksEmpty.forEach { fc2Id ->
            val links = sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)
            magnetLinks[fc2Id] = links
        }
        magnetLinksRepository.saveByVideoInfoId(magnetLinks)
    }
}