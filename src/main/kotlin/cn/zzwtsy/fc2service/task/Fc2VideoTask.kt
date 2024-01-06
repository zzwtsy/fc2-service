package cn.zzwtsy.fc2service.task

import cn.zzwtsy.fc2service.model.VideoInfo
import cn.zzwtsy.fc2service.model.by
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.repository.MagnetLinksRepository
import cn.zzwtsy.fc2service.service.Fc2VideoInfoService
import cn.zzwtsy.fc2service.service.SukebeiNyaaHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.babyfish.jimmer.kt.new
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class Fc2VideoTask {
    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var fc2VideoInfoService: Fc2VideoInfoService

    @Autowired
    private lateinit var magnetLinksRepository: MagnetLinksRepository

    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @Autowired
    private lateinit var sukebeiNyaaHTMLParseService: SukebeiNyaaHTMLParseService

    // 每间隔 8 小时执行一次
    // @Scheduled(fixedDelay = 8, timeUnit = TimeUnit.HOURS)
    fun executeGetNewVideoInfoTask() {
        val fc2VideoInfo = fc2VideoInfoService.getFc2VideoInfo()
        fc2VideoInfoRepository.saveAll(fc2VideoInfo)
    }

    // @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    fun executeGetVideoMagnetLinksTask() {
        // 检查视频信息中的磁力链接是否为空
        val magnetLinksEmpty = fc2VideoInfoRepository.queryVideoInfoMagnetLinksIsEmpty()

        // 创建一个空的可变映射用于存储视频的磁力链接
        val videoInfos = mutableListOf<VideoInfo>()

        // 初始化计数器
        var count = 1

        // 遍历磁力链接为空的视频
        for (fc2Id in magnetLinksEmpty) {
            // 获取视频的磁力链接,如果获取到的磁力链接为空，则跳过循环
            val magnetLinks = sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)
            if (magnetLinks.isEmpty()) {
                logger.warn { "$fc2Id 暂无磁力链接" }
                continue
            }

            // 将视频的fc2Id和对应的磁力链接添加到映射中
            videoInfos.add(
                new(VideoInfo::class).by {
                    videoId = fc2Id
                    this.magnetLinks = magnetLinks
                }
            )

            count++

            // 每获取50个视频的磁力链接，保存一次映射，并清空映射，暂停3秒
            if (count % 50 == 0 || count == magnetLinksEmpty.size) {
                logger.info { "已获取 $count 个视频的磁力链接，正在保存..." }
                try {
                    saveMagnetLinks(videoInfos)
                } finally {
                    videoInfos.clear()
                }
                logger.info { "保存磁力链接完成，正在等待3秒..." }
                TimeUnit.SECONDS.sleep(3)
            }
        }
        logger.info { "获取磁力链接任务执行完毕" }
        if (videoInfos.isNotEmpty()) {
            saveMagnetLinks(videoInfos)
        }
    }

    private fun saveMagnetLinks(videoInfos: List<VideoInfo>) {
        try {
            magnetLinksRepository.saveAll(videoInfos)
        } catch (e: Exception) {
            logger.error(e) { "保存磁力链接时发生异常: ${e.message}" }
        }
    }
}