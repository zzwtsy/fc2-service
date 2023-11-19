package cn.zzwtsy.fc2service.api

import cn.zzwtsy.fc2service.utils.HttpUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.Headers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.*


/**
 * FC2 api
 * @author zzwtsy
 * @date 2023/11/05
 * @constructor 创建[Fc2Api]
 */
@Component
class Fc2Api {
    companion object {
        private val logger = KotlinLogging.logger { }

        // 定义 API_URL 常量
        private const val FC2_API_URL = "https://adult.contents.fc2.com/article/"
        private const val FC2HUB_API_URL = "https://fc2hub.com/search?kw="
        private const val ITEMS_NOT_FOUND_XPATH = "/html/body/div[3]/div/div[1]"
        private val userAgent = listOf(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:108.0) Gecko/20100101 Firefox/108.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64; rv:108.0) Gecko/20100101 Firefox/108.0",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.2 Safari/605.1.15",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:108.0) Gecko/20100101 Firefox/108.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/109.0",
        )
    }

    /**
     * 获取 FC2 视频页面 html
     * @param [fc2ID] FC2 id
     * @return [Document]
     */
    fun getFc2VideoPageHtml(fc2ID: String): Document? {
        return try {
            val fc2VideoUrl = getFc2VideoUrl(fc2ID)
            val sendGet = HttpUtil.sendGet(fc2VideoUrl, headers = getFc2VideoPageHeaders())
            val body = sendGet.body
            if (!sendGet.isSuccessful || body == null) return null
            val parse = Jsoup.parse(body.string())
            val itemsNotFound = parse.selectXpath(ITEMS_NOT_FOUND_XPATH)
            if (itemsNotFound.isEmpty()) null else parse
        } catch (e: Exception) {
            logger.error(e) { "获取 FC2 视频页面 html 失败" }
            null
        }
    }

    fun getFc2HubVideoPageHtml(fc2ID: String): Document? {
        return try {
            val fc2HubVideoUrl = getFc2HubVideoUrl(fc2ID)
            val sendGet = HttpUtil.sendGet(fc2HubVideoUrl, headers = getFc2VideoPageHeaders())
            val body = sendGet.body
            if (!sendGet.isSuccessful || body == null) return null
            Jsoup.parse(body.string())
        } catch (e: Exception) {
            logger.error(e) { "获取 FC2HUB 视频页面 html 失败" }
            null
        }
    }

    private fun getFc2VideoPageHeaders(): Headers {
        return Headers.headersOf(
            "User-Agent",
            userAgent[Random().nextInt(userAgent.size)]
        )
    }

    /**
     * 获取 FC2 视频页面 url
     * @param [fc2ID] FC2 id
     * @return [String]
     */
    private fun getFc2VideoUrl(fc2ID: String): String {
        if (!isFc2Id(fc2ID)) return ""
        // 返回拼接后的 Article URL
        return "$FC2_API_URL${fc2ID}/"
    }

    private fun getFc2HubVideoUrl(fc2ID: String): String {
        if (!isFc2Id(fc2ID)) return ""
        return "${FC2HUB_API_URL}/$fc2ID"
    }

    private fun isFc2Id(fc2ID: String): Boolean {
        return if (fc2ID.length != 7) {
            logger.error { "Invalid FC2 ID: $fc2ID" }
            false
        } else {
            logger.debug { "Valid FC2 ID: $fc2ID" }
            true
        }
    }
}
