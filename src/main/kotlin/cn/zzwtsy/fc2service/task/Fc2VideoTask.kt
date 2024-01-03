package cn.zzwtsy.fc2service.task

import cn.zzwtsy.fc2service.model.MagnetLinks
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.repository.MagnetLinksRepository
import cn.zzwtsy.fc2service.service.GetFc2VideoInfo
import cn.zzwtsy.fc2service.service.SukebeiNyaaHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

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
    @Scheduled(fixedDelay = 8, timeUnit = TimeUnit.HOURS)
    fun executeGetNewVideoInfoTask() {
        val fc2VideoInfo = getFc2VideoInfo.getFc2VideoInfo()
        fc2VideoInfoRepository.saveAll(fc2VideoInfo)
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    fun executeGetVideoMagnetLinksTask() {
        val magnetLinksEmpty = fc2VideoInfoRepository.queryVideoInfoMagnetLinksIsEmpty()
        val magnetLinks = mutableMapOf<Long, List<MagnetLinks>>()
        magnetLinksEmpty.forEach { fc2Id ->
            val links = sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)
            magnetLinks[fc2Id] = links
        }
        magnetLinksRepository.saveByVideoInfoId(magnetLinks)
    }
}