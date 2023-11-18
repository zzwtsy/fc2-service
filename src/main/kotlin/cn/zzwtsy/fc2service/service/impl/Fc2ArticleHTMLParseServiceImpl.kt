package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.service.Fc2ArticleHTMLParseService
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

/**
 * 从 fc2.com 获取 fc2 视频的信息
 *
 * 目前有以下问题:
 * 1. 部分视频锁地区无法获取视频信息
 * 2. fc2 视频会出现下架
 *
 * 所以需要其他 fc2 视频网站补充缺失视频的信息
 * @author zzwtsy
 * @date 2023/11/15
 * @constructor 创建[Fc2ArticleHTMLParseServiceImpl]
 */
@Service
class Fc2ArticleHTMLParseServiceImpl : Fc2ArticleHTMLParseService {
    companion object {
        private const val TITLE_XPATH = "/html/head/title"
        private const val STUDIO_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/ul/li[3]/a"
        private const val RELEASE_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/div[2]/p"
        private const val RUNTIME_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[1]/span/p"
        private const val SELLER_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/ul/li[3]/a"
        private const val ACTOR_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/ul/li[3]/a"
        private const val COVER_XPATH = "//div[@class='items_article_MainitemThumb']/span/img"
        private const val PREVIEW_PICTURES_XPATH = "//ul[@class=\"items_article_SampleImagesArea\"]/li/a"
        private const val TAGS_XPATH = "//a[@class='tag tagTag']"
    }

    /**
     * 解析
     * @param [html] html
     * @return [String]
     */
    override fun parse(html: Document?): String {
        if (html == null) {
            println("非常抱歉，此商品未在您的居住国家公开。")
            return ""
        }
        val title = html.selectXpath(TITLE_XPATH).text()
        val studio = html.selectXpath(STUDIO_XPATH).text()
        val release = html.selectXpath(RELEASE_XPATH).text()
        val runtime = html.selectXpath(RUNTIME_XPATH).text()
        val seller = html.selectXpath(SELLER_XPATH).text()
        val actor = html.selectXpath(ACTOR_XPATH).text()
        val cover = html.selectXpath(COVER_XPATH).attr("src")
        val previewPictures = html.selectXpath(PREVIEW_PICTURES_XPATH).map { it.attr("href") }
        val tags = html.selectXpath(TAGS_XPATH).text().split(" ")
        println(
            """
                title: $title
                studio: $studio
                release: $release
                runtime: $runtime
                director: $seller
                actor: $actor
                cover: $cover
                previewPictures: $previewPictures
                tags: $tags
            """.trimIndent()
        )
        return ""
    }
}