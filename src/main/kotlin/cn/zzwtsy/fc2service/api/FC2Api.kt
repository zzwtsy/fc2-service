package cn.zzwtsy.fc2service.api

import org.jsoup.Jsoup
import org.jsoup.nodes.Document


/**
 * FC2 api
 * @author zzwtsy
 * @date 2023/11/05
 * @constructor 创建[FC2Api]
 */
class FC2Api {
    companion object {
        // 定义 API_URL 常量
        private const val FC2_API_URL = "https://adult.contents.fc2.com/article/"
        // private const val FC2HUB_API_URL = "https://fc2hub.com/search?kw="
    }

    /**
     * 获取 FC2 视频页面 url
     * @param [fc2ID] FC2 id
     * @return [String]
     */
    @Throws(IllegalArgumentException::class)
    private fun getFC2VideoUrl(fc2ID: Long): String {
        if (fc2ID.toString().length != 7) {
            // 抛出异常，提示 fc2ID 不是有效的 FC2 id
            throw IllegalArgumentException("FC2ID is not a valid FC2 id")
        }
        // 返回拼接后的 Article URL
        return "$FC2_API_URL${fc2ID}/"
    }

    /**
     * 获取 FC2 视频页面 html
     * @param [fc2ID] FC2 id
     * @return [Document]
     */
    fun getFC2VideoPageHtml(fc2ID: Long): Document? {
        val fC2VideoUrl = getFC2VideoUrl(fc2ID)
        val body = Jsoup.connect(fC2VideoUrl).ignoreContentType(true).execute().body()
        val parse = Jsoup.parse(body)
        val countryUnsupported = parse.selectXpath("/html/body/div[3]/div/div[1]/h3").text()
        return if (countryUnsupported.isNotEmpty()) {
            null
        } else {
            parse
        }
    }
}
