package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.service.Fc2ArticleHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
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
        private val logger = KotlinLogging.logger { }
        private const val TITLE_XPATH = "/html/head/title"
        private const val TAGS_XPATH = "//*[@id=\"content\"]/div/div[3]/div[1]/div[1]/div[2]/p/a"
        private const val SELLER_XPATH = "//*[@id=\"content\"]/div/div[3]/div[2]/div[1]/div[2]/div/div[2]"
        private const val PREVIEW_PICTURES_XPATH = "//*[@id=\"content\"]/div/div[3]/div[2]/div[4]/div/div"
        private const val POST_XPATH = "//*[@id=\"content\"]/div/div[3]/div[1]/div[1]/div[2]/div[2]/div"

        private val TITLE_REGEX = Regex("\\[FC2-PPV-\\d+]|-\\sFc2hub\\.com", RegexOption.IGNORE_CASE)
    }

    override fun parse(html: Document): String {
        val title = getTitle(html)
        val tags = getTags(html)
        val seller = getSeller(html)
        val previewPictures = getPreviewPictures(html)
        val cover = previewPictures.firstOrNull()


        return ""
    }

    /**
     * 获取标题
     * @param [html] html
     * @return [String]
     */
    override fun getTitle(html: Document): String {
        return try {
            html.selectXpath(TITLE_XPATH).text().replace(TITLE_REGEX, "").trim()
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
        TODO("Not yet implemented")
    }

    /**
     * 获取预览图片
     * @param [html] html
     * @return [List<String>]
     */
    override fun getPreviewPictures(html: Document): List<String> {
        return try {
            html.selectXpath(PREVIEW_PICTURES_XPATH)
                .map { it.select("div > a").attr("src") }
                .ifEmpty { getPostImage(html) }
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
        TODO("Not yet implemented")
    }

    private fun getPostImage(html: Document): List<String> {
        return try {
            val elements = html.selectXpath(POST_XPATH)
            elements.map { it.select("a").attr("href") }
        } catch (e: Exception) {
            logger.error(e) { "预览图失败" }
            listOf()
        }
    }

}