package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.service.Fc2ArticleHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
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
        private val logger = KotlinLogging.logger { }
        private const val TITLE_XPATH = "/html/head/title"
        private const val SELLER_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/ul/li[3]/a"
        private const val RELEASE_XPATH = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/div[2]/p"
        private const val COVER_XPATH = "//div[@class='items_article_MainitemThumb']/span/img"
        private const val PREVIEW_PICTURES_XPATH = "//ul[@class=\"items_article_SampleImagesArea\"]/li/a"
        private const val TAGS_XPATH = "//a[@class='tag tagTag']"
    }

    /**
     * 解析
     * @param [html] html
     * @return [String]
     */
    override fun parse(html: Document): String {
        val title = getTitle(html)
        val releaseDate = getReleaseDate(html)
        val seller = getSeller(html)
        val cover = getCover(html)
        val previewPictures = getPreviewPictures(html)
        val tags = getTags(html)
        println(
            """
                title: $title
                release: $releaseDate
                seller: $seller
                cover: $cover
                previewPictures: $previewPictures
                tags: $tags
            """.trimIndent()
        )

        return ""
    }

    /**
     * 获取标题
     * @param [html] html
     * @return [String]
     */
    override fun getTitle(html: Document): String {
        return try {
            html.selectXpath(TITLE_XPATH).text()
        } catch (e: Exception) {
            logger.error(e) { "获取标题失败" }
            ""
        }
    }

    /**
     * 获取标签
     * @param [html] html
     * @return [List<String>]
     */
    override fun getTags(html: Document): List<String> {
        return try {
            html.selectXpath(TAGS_XPATH).map { it.text() }
        } catch (e: Exception) {
            logger.error(e) { "获取标签失败" }
            listOf()
        }
    }

    /**
     * 获取卖家
     * @param [html] html
     * @return [String]
     */
    override fun getSeller(html: Document): String {
        return try {
            html.selectXpath(SELLER_XPATH).text()
        } catch (e: Exception) {
            logger.error(e) { "获取卖家失败" }
            ""
        }
    }

    /**
     * 获取封面
     * @param [html] html
     * @return [String]
     */
    override fun getCover(html: Document): String {
        return try {
            html.selectXpath(COVER_XPATH).attr("src")
        } catch (e: Exception) {
            logger.error(e) { "获取封面失败" }
            ""

        }
    }

    /**
     * 获取预览图片
     * @param [html] html
     * @return [List<String>]
     */
    override fun getPreviewPictures(html: Document): List<String> {
        return try {
            html.selectXpath(PREVIEW_PICTURES_XPATH).map { it.attr("href") }
        } catch (e: Exception) {
            logger.error(e) { "获取预览图片失败" }
            listOf()
        }
    }

    /**
     * 获取发布日期
     * @param [html] html
     * @return [String]
     */
    override fun getReleaseDate(html: Document): String {
        return try {
            html.selectXpath(RELEASE_XPATH).text()
        } catch (e: Exception) {
            logger.error(e) { "获取发布日期失败" }
            ""
        }
    }
}
