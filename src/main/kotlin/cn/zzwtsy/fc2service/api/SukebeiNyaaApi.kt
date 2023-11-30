package cn.zzwtsy.fc2service.api

import cn.zzwtsy.fc2service.utils.HttpUtil
import cn.zzwtsy.fc2service.utils.Util
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class SukebeiNyaaApi {
    private val logger = KotlinLogging.logger { }
    private val sukebeiNyaaRssUrl = "https://sukebei.nyaa.si/?page=rss&q=FC2&c=2_2&f=2"
    private val sukebeiNyaaSearchUrl = "https://worker-fancy-dream-ee62.m3226257319.workers.dev/?q="

    fun getRSS(): SyndFeed? {
        val response = HttpUtil.sendGet(sukebeiNyaaRssUrl).body?.byteStream() ?: return null
        return SyndFeedInput().build(XmlReader(response))
    }

    fun searchByFc2Id(fc2Id: String): Document? {
        return try {
            val response = HttpUtil.sendGet("${sukebeiNyaaSearchUrl}$fc2Id", Util.getFc2VideoPageHeaders())
            if (!response.isSuccessful || response.body == null) return null
            val body = response.body?.string() ?: return null
            Jsoup.parse(body)
        } catch (e: IOException) {
            logger.error(e) { "获取 $fc2Id 视频页面失败" }
            null
        }
    }

}