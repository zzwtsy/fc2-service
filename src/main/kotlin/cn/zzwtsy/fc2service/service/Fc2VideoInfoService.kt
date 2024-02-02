package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.Fc2Api
import cn.zzwtsy.fc2service.model.VideoInfo
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.utils.Fc2Headers
import cn.zzwtsy.fc2service.utils.HttpUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Fc2VideoInfoService {
    private val logger = KotlinLogging.logger { }

    private val itemXpath = "//*[@id=\"pjx-container\"]/div[2]/div[2]/section[2]/div[@class=\"c-cntCard-110-f\"]"
    private val titleXpath = "/html/head/title"
    private val itemFc2IdSelector = ".c-cntCard-110-f_thumb > a"
    private val numberRegex = "\\d+".toRegex()
    private val loginTitle = listOf(
        "登录 FC2 - 免费主页 访问分析 博客 服务器租赁 SEO对策 等 -",
        "登入 FC2 - 免費主頁 訪問分析 部落格 伺服器租賃 SEO對策 等 -",
        "Log in  FC2 - Free Website Access Analysis Blog Rental Server SEO Countermeasures etc. -",
        "ログイン　FC2 - 無料ホームページ アクセス解析 ブログ レンタルサーバー SEO 対策 等 -"
    )

    @Autowired
    private lateinit var fc2Api: Fc2Api

    @Autowired
    private lateinit var fc2VideoInfoParseService: Fc2VideoInfoParseService

    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    fun getFc2VideoInfo(): List<VideoInfo> {
        val fc2IdList = getFc2NewIdList()
        val videoInfoSet = mutableSetOf<VideoInfo>()
        for (fc2Id in fc2IdList) {
            try {
                val videoInfo = fc2VideoInfoParseService.parse(fc2Id)
                if (videoInfo != null) {
                    videoInfoSet.add(videoInfo)
                }
            } catch (e: Exception) {
                logger.warn { "获取 $fc2Id 视频信息时发生异常: ${e.message}" }
            }
        }

        if (videoInfoSet.isEmpty()) {
            logger.info { "获取到的视频信息为空" }
            return emptyList()
        }

        val imagePathToUrl = mutableMapOf<String, String>()

        videoInfoSet.forEach { info ->
            val releaseDate = info.releaseDate.toString().replace("-", "/")
            imagePathToUrl["${releaseDate}/${info.videoId}/cover"] = "${info.covers?.coverUrl}"

            info.previewPictures.forEachIndexed { index, picture ->
                imagePathToUrl["${releaseDate}/${info.videoId}/previewPictures_$index"] = picture.pictureUrl
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            saveImages(imagePathToUrl)
        }.invokeOnCompletion {
            logger.info { "保存图片完成" }
        }

        logger.info { "获取 FC2 视频信息成功，共 ${videoInfoSet.size} 个视频" }
        return videoInfoSet.toList()
    }


    /**
     * 获取最新的 FC2 个视频列表的 ID
     *
     * @return 最新的 FC2 个视频列表的 ID 列表
     */
    private fun getFc2NewIdList(): List<Long> {
        logger.info { "开始获取最新的 FC2 个视频列表" }
        val result = mutableListOf<Long>()
        var pageIndex = 1
        while (true) {
            // 获取最新的 FC2 个视频页面的 HTML
            val documents = fc2Api.getFc2VideoPageHtmlByDescDate(pageIndex)
            // 如果获取页面为空，则记录警告并返回空列表
            if (documents == null) {
                logger.warn { "获取最新的 FC2 个视频列表失败" }
                break
            }
            if (documents.selectXpath(titleXpath).text() in loginTitle) {
                logger.warn { "获取最新的 FC2 个视频列表失败，网站需要登录" }
                break
            }

            // 处理每个页面的视频 ID
            val ids = parserFc2NewVideoPage(documents)
            if (ids.isEmpty()) {
                logger.warn { "获取最新的 FC2 个视频列表失败，页面中没有视频" }
                break
            }

            // 查询并排除已存在的视频 ID
            val idsExcluding = fc2VideoInfoRepository.queryVideoIdsExcluding(ids)
            logger.info { "获取最新的 FC2 个视频列表成功，共 ${ids.size} 个视频，剩余 ${idsExcluding.size} 个视频" }

            result.addAll(idsExcluding)
            if (ids.size == idsExcluding.size) {
                pageIndex++
            } else {
                break
            }
        }

        return result
    }

    /**
     * 解析 FC2 页面的视频 ID
     *
     * @param document FC2 页面的 HTML 文档
     * @return FC2 页面的视频 ID 列表
     */
    private fun parserFc2NewVideoPage(document: Document): List<Long> {
        // 获取页面的根节点
        val body = document.body()
        // 选择页面的特定元素
        val items = body.selectXpath(itemXpath).asSequence()

        // 处理每个元素的视频 ID，并去重后过滤掉零值
        val ids = items
            .filter { element -> element.select(itemFc2IdSelector).attr("href").startsWith("/article") }
            .map { element ->
                val href = element.select(itemFc2IdSelector).attr("href")
                numberRegex.find(href)?.value?.toLong() ?: 0L
            }
        return ids.distinct().filter { it != 0L }.toList()
    }

    private suspend fun saveImages(imageNameToUrl: Map<String, String>) {
        // 记录开始获取图片的日志
        logger.info { "开始获取图片" }
        val headers = Headers.headersOf(
            "Cookie", Fc2Headers.cookie,
            "User-Agent", Fc2Headers.userAgent,
        )

        // 遍历imageNameToUrl中的每一对键值对
        imageNameToUrl.filter { (filePathName, url) ->
            filePathName.isNotEmpty() && url.startsWith("http")
        }.forEach { (filePathName, url) ->
            // 下载图片
            HttpUtil.downloadImage(filePathName, url, headers)
        }
    }
}