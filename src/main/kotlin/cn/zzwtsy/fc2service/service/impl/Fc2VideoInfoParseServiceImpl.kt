package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.api.Fc2Api
import cn.zzwtsy.fc2service.domain.dto.Fc2VideoInfoDto
import cn.zzwtsy.fc2service.domain.dto.MagnetLinksDto
import cn.zzwtsy.fc2service.service.SukebeiNyaaHTMLParseService
import cn.zzwtsy.fc2service.utils.Util.toLocalDate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

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
 * @constructor 创建[Fc2VideoInfoParseServiceImpl]
 */
@Service
class Fc2VideoInfoParseServiceImpl : Fc2VideoInfoParseBase() {
    private val logger = KotlinLogging.logger { }

    private val titleXpath = "/html/head/title"
    private val sellerXpath = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/ul/li"
    private val releaseDateXpath = "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/div[2]/p"
    private val coverXpath = "//div[@class='items_article_MainitemThumb']/span/img"
    private val previewPicturesXpath = "//ul[@class=\"items_article_SampleImagesArea\"]/li"
    private val tagsXpath = "//a[@class='tag tagTag']"
    private val fc2Regex = Regex("FC2-PPV-\\d+", RegexOption.IGNORE_CASE)
    private val dateRegex = Regex("\\d{4}/\\d{2}/\\d{2}")

    @Autowired
    private lateinit var fc2Api: Fc2Api

    @Autowired
    private lateinit var sukebeiNyaaHTMLParseService: SukebeiNyaaHTMLParseService

    /**
     * 解析
     * @param [fc2Id] FC2 ID
     * @return [String]
     */
    override fun parse(fc2Id: Int): Fc2VideoInfoDto? {
        val fc2videoHtml = fc2Api.getFc2VideoPageHtmlByFc2Id(fc2Id) ?: return null
        val title = getTitle(fc2videoHtml)
        val releaseDate = getReleaseDate(fc2videoHtml).toLocalDate("yyyy/MM/dd")
        val seller = getSeller(fc2videoHtml)
        val cover = getCover(fc2videoHtml)
        val magnetLinks = if (releaseDate.isAfter(LocalDate.now())) getMagnetLinks(fc2Id) else emptyList()
        val previewPictures = getPreviewPictures(fc2videoHtml)
        val tags = getTags(fc2videoHtml)

        return Fc2VideoInfoDto(
            fc2Id,
            title = title,
            releaseDate = releaseDate,
            sellers = listOf(seller),
            magnetLinks = magnetLinks,
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
            html.selectXpath(sellerXpath)
                .filter { li -> li.text().contains("by") }
                .map { it.select("li > a").text() }
                .first()
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
            val selectXpath = html.selectXpath(previewPicturesXpath)
            selectXpath
                .map {
                    val attr = it.select("a").attr("href")
                    when {
                        attr.startsWith("https") -> attr
                        attr.startsWith("http") -> attr
                        else -> "https:${attr}"
                    }
                }
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
            val text = html.selectXpath(releaseDateXpath).text()
            val dateString = dateRegex.find(text)?.value
            return if (dateString.isNullOrEmpty()) {
                ""
            } else {
                dateString
            }
        } catch (e: Exception) {
            logger.error(e) { "获取发布日期失败" }
            ""
        }
    }

    override fun getMagnetLinks(fc2Id: Int): List<MagnetLinksDto> {
        return sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)
    }
}