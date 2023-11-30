package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.api.Fc2Api
import cn.zzwtsy.fc2service.dto.Fc2VideoInfoDto
import cn.zzwtsy.fc2service.service.Fc2ArticleHTMLParseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
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
    private val logger = KotlinLogging.logger { }

    private val titleXpath = "/html/head/title"
    private val sellerXpath = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/ul/li/a"
    private val releaseXpath = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/div[2]/p"
    private val coverXpath = "//div[@class='items_article_MainitemThumb']/span/img"
    private val previewPicturesXpath = "//ul[@class=\"items_article_SampleImagesArea\"]/li/a"
    private val tagsXpath = "//a[@class='tag tagTag']"
    private val fc2Regex = Regex("FC2-PPV-\\d+", RegexOption.IGNORE_CASE)
    private val dateRegex = Regex("\\d{4}/\\d{2}/\\d{2}")

    @Autowired
    private lateinit var fc2Api: Fc2Api

    /**
     * 解析
     * @param [fc2Id] FC2 ID
     * @return [String]
     */
    override fun parse(fc2Id: String): Fc2VideoInfoDto? {
        val fc2videoHtml = fc2Api.getFc2VideoPageHtmlByFc2Id(fc2Id) ?: return null
        val title = getTitle(fc2videoHtml)
        val releaseDate = getReleaseDate(fc2videoHtml)
        val seller = getSeller(fc2videoHtml)
        val cover = getCover(fc2videoHtml)
        val previewPictures = getPreviewPictures(fc2videoHtml)
        val tags = getTags(fc2videoHtml)
        return Fc2VideoInfoDto(
            title = title,
            releaseDate = releaseDate,
            seller = seller,
            cover = cover,
            previewPictures = previewPictures,
            tags = tags
        )
    }

    /**
     * 获取标题
     * @param [html] html
     * @return [String]
     */
    override fun getTitle(html: Document): String {
        return try {
            html.selectXpath(titleXpath).text()
                .replace(fc2Regex, "").trim()
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
            html.selectXpath(tagsXpath).map { it.text() }
        } catch (e: Exception) {
            logger.error(e) { "获取标签失败" }
            emptyList()
        }
    }

    /**
     * 获取卖家
     * @param [html] html
     * @return [String]
     */
    override fun getSeller(html: Document): String {
        return try {
            html.selectXpath(sellerXpath).text()
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
            val attr = html.selectXpath(coverXpath).attr("src")
            "https:${attr}"
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
            html.selectXpath(previewPicturesXpath).map { "https:${it.attr("href")}" }
        } catch (e: Exception) {
            logger.error(e) { "获取预览图片失败" }
            emptyList()
        }
    }

    /**
     * 获取发布日期
     * @param [html] html
     * @return [String]
     */
    override fun getReleaseDate(html: Document): String {
        return try {
            val text = html.selectXpath(releaseXpath).text()
            dateRegex.find(text)?.value ?: ""
        } catch (e: Exception) {
            logger.error(e) { "获取发布日期失败" }
            ""
        }
    }
}
