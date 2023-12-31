package cn.zzwtsy.fc2service.api

import cn.zzwtsy.fc2service.utils.HttpUtil
import cn.zzwtsy.fc2service.utils.Util
import cn.zzwtsy.fc2service.utils.Util.getFc2VideoPageHeaders
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component


/**
 * FC2 api
 * @author zzwtsy
 * @date 2023/11/05
 * @constructor 创建[Fc2Api]
 */
@Component
class Fc2Api {
    private val logger = KotlinLogging.logger { }

    // 定义 API_URL 常量
    private val apiBaseUrl = "https://adult.contents.fc2.com"

    // https://adult.contents.fc2.com/article/2763672/
    private val apiArticleUrl = "${apiBaseUrl}/article"
    private val fc2NewVideosUrl = "${apiBaseUrl}/search/?sort=date&order=dsec&page="


    /**
     * 获取 FC2 视频页面 html 按日期由新至旧
     * @param [pageNumber] 页数
     * @return [Document]
     */
    fun getFc2VideoPageHtmlByDescDate(pageNumber: Int = 1): Document? {
        val sendGet = HttpUtil.sendGet(fc2NewVideosUrl, headers = getFc2VideoPageHeaders())
        val body = sendGet.body
        if (!sendGet.isSuccessful || body == null) return null
        return Jsoup.parse(body.string())
    }

    /**
     * 通过 FC2 id 获取 FC2 视频页面
     * @param [fc2Id] FC2 ID
     * @return [Document]
     */
    fun getFc2VideoPageHtmlByFc2Id(fc2Id: Long): Document? {
        if (!Util.isFc2Id(fc2Id)) {
            return null
        }
        return try {
            val sendGet = HttpUtil.sendGet("$apiArticleUrl/$fc2Id/", headers = getFc2VideoPageHeaders())
            val body = sendGet.body
            if (!sendGet.isSuccessful || body == null) return null
            Jsoup.parse(body.string())
        } catch (e: Exception) {
            logger.error(e) { "获取 $fc2Id 视频页面失败" }
            null
        }
    }

}
