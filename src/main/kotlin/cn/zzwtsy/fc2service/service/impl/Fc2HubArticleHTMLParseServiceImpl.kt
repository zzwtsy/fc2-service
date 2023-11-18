package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.service.Fc2ArticleHTMLParseService
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

/**
 * 从 fc2hub.com 获取 fc2 视频的信息,
 * 用于获取 fc2.com 网站获取不到的视频信息
 *
 * @author zzwtsy
 * @date 2023/11/18
 * @constructor 创建[Fc2HubArticleHTMLParseServiceImpl]
 */
@Service
class Fc2HubArticleHTMLParseServiceImpl : Fc2ArticleHTMLParseService {
    companion object {
        private const val TITLE_XPATH = "/html/head/title"
        private const val TAGS_XPATH = "//*[@id=\"content\"]/div/div[3]/div[1]/div[1]/div[2]/p/a"

        private val TITLE_REGEX = Regex("\\[FC2-PPV-\\d+]|-\\sFc2hub\\.com", RegexOption.IGNORE_CASE)
    }

    override fun parse(html: Document?): String {
        if (html == null) {
            println("非常抱歉，此商品未在您的居住国家公开。")
            return ""
        }
        val title = html.selectXpath(TITLE_XPATH).text().replace(TITLE_REGEX, "").trim()
        val tags = html.selectXpath(TAGS_XPATH).map { it.text() }
        val seller = ""

        return ""
    }
}