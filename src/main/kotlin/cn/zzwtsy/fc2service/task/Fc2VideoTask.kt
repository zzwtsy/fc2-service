package cn.zzwtsy.fc2service.task

import cn.zzwtsy.fc2service.model.MagnetLinks
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.repository.MagnetLinksRepository
import cn.zzwtsy.fc2service.service.GetFc2VideoInfo
import cn.zzwtsy.fc2service.service.SukebeiNyaaHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
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
        val fc2VideoInfo = getFc2VideoInfo.getFc2VideoInfo()
        fc2VideoInfoRepository.saveAll(fc2VideoInfo)
    }

    // @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    suspend fun executeGetVideoMagnetLinksTask() {
        // 检查视频信息中的磁力链接是否为空
        val magnetLinksEmpty = fc2VideoInfoRepository.queryVideoInfoMagnetLinksIsEmpty()

        // 创建一个空的可变映射用于存储视频的磁力链接
        val magnetLinks = mutableMapOf<Long, List<MagnetLinks>>()

        // 初始化计数器
        var count = 0

        // 遍历磁力链接为空的视频
        magnetLinksEmpty.forEach { fc2Id ->
            // 获取视频的磁力链接
            val links = sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)

            // 如果获取到的磁力链接为空，则终止遍历
            if (links.isEmpty()) return@forEach

            // 将视频的fc2Id和对应的磁力链接添加到映射中
            magnetLinks[fc2Id] = links

            // 计数器加一
            count++

            // 每获取50个视频的磁力链接，保存一次映射，并清空映射，暂停3秒
            if (count % 50 == 0) {
                logger.info { "已获取 $count 个视频的磁力链接，正在保存..." }
                try {
                    magnetLinksRepository.saveAll(magnetLinks)
                } catch (e: Exception) {
                    logger.error(e) { "保存磁力链接时发生异常: ${e.message}" }
                } finally {
                    magnetLinks.clear()
                }
                logger.info { "保存磁力链接完成，正在等待3秒..." }
                delay(3000)
            }
        }
    }
}