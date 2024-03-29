package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.api.Fc2Api
import cn.zzwtsy.fc2service.model.*
import cn.zzwtsy.fc2service.service.SukebeiNyaaHTMLParseService
import cn.zzwtsy.fc2service.utils.Util.toLocalDate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.babyfish.jimmer.kt.new
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

    private val releaseDateXpath =
        "//*[@id=\"top\"]/div[1]/section[1]/div/section/div[2]/div[@class=\"items_article_Releasedate\"]/p"
    private val coverXpath = "//div[@class='items_article_MainitemThumb']/span/img"
    private val previewPicturesXpath = "//ul[@class=\"items_article_SampleImagesArea\"]/li"
    private val tagsXpath = "//a[@class='tag tagTag']"
    private val fc2Regex = Regex("FC2-PPV-\\d+", RegexOption.IGNORE_CASE)
    private val dateRegex = Regex("\\d{4}/\\d{2}/\\d{2}")
    private val notFoundVideo = listOf(
        "没有发现您要找的商品", "お探しの商品が見つかりません", "Unable to find Product."
    )

    @Autowired
    private lateinit var fc2Api: Fc2Api

    @Autowired
    private lateinit var sukebeiNyaaHTMLParseService: SukebeiNyaaHTMLParseService

    /**
     * 解析
     * @param [fc2Id] FC2 ID
     * @return [String]
     */
    override fun parse(fc2Id: Long): VideoInfo? {
        val fc2videoHtml = fc2Api.getFc2VideoPageHtmlByFc2Id(fc2Id)
        if (fc2videoHtml == null) {
            logger.warn { "获取 $fc2Id 视频信息失败" }
            return null
        }
        val title = getTitle(fc2videoHtml)
        if (notFoundVideo.contains(title)) {
            logger.warn { "获取 $fc2Id 视频信息失败: 该视频已下架" }
            return null
        }
        val date = getReleaseDate(fc2videoHtml)
        val releaseDate = if (date.isNotEmpty()) date.toLocalDate("yyyy/MM/dd") else null

        val seller = getSeller(fc2videoHtml)
        val cover = getCover(fc2videoHtml)
        // 如果发布日期在今天之前，获取磁力链接
        val magnetLinks = if (releaseDate != null && releaseDate.isAfter(LocalDate.now())) {
            getMagnetLinks(fc2Id)
        } else {
            emptyList()
        }
        val previewPictures = getPreviewPictures(fc2videoHtml)
        val tags = getTags(fc2videoHtml)

        val videoInfo = new(VideoInfo::class).by {
            this.videoId = fc2Id
            this.title = title
            this.releaseDate = releaseDate
            this.sellers = seller
            this.covers = cover
            this.magnetLinks = magnetLinks
            this.previewPictures = previewPictures
            this.tags = tags
        }

        return videoInfo
    }

    /**
     * 获取标题
     * @param [html] html
     * @return [String]
     */
    override fun getTitle(html: Document): String {
        return try {
            html.selectXpath(titleXpath).text().replace(fc2Regex, "").trim()
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
    override fun getTags(html: Document): List<Tags> {
        return try {
            html.selectXpath(tagsXpath).flatMap { element ->
                val text = element.text()
                when {
                    text.isEmpty() -> emptyList()
                    // 判断是否包含多个标签
                    text.contains(',') -> {
                        text.split(",")
                            .filter { tag -> tag.isNotBlank() }
                            .map { new(Tags::class).by { this.tag = it } }
                    }

                    else -> listOf(new(Tags::class).by { this.tag = text })
                }
            }
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
    override fun getSeller(html: Document): List<Sellers> {
        return try {
            html.selectXpath(sellerXpath).filter { li -> li.text().contains("by") }.map {
                val a = it.select("li > a")
                val seller = a.text()
                val sellerWebSiteId = a
                    // https://adult.contents.fc2.com/users/kakumei/
                    .attr("href")
                    .replace(Regex("https://.+?/users/"), "")
                    .replace("/", "")
                new(Sellers::class).by {
                    this.seller = seller
                    this.sellerWebSiteId = sellerWebSiteId
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "获取卖家失败" }
            emptyList()
        }
    }

    /**
     * 获取封面
     * @param [html] html
     * @return [String]
     */
    override fun getCover(html: Document): Covers? {
        return try {
            val attr = html.selectXpath(coverXpath).attr("src")
                .replace(Regex("contents-thumbnail2\\.fc2\\.com/[a-z|A-Z\\d]+/"), "")
            new(Covers::class).by { this.coverUrl = "https:${attr}" }
        } catch (e: Exception) {
            logger.error(e) { "获取封面失败" }
            null
        }
    }

    /**
     * 获取预览图片
     * @param [html] html
     * @return [List<String>]
     */
    override fun getPreviewPictures(html: Document): List<PreviewPictures> {
        return try {
            val selectXpath = html.selectXpath(previewPicturesXpath)
            selectXpath.map {
                val attr = it.select("a").attr("href")
                val url = when {
                    attr.startsWith("https") -> attr
                    attr.startsWith("http") -> attr
                    else -> "https:${attr}"
                }
                new(PreviewPictures::class).by { this.pictureUrl = url }
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
                val xpath = "//meta[@property=\"og:url\"]"
                val elements = html.selectXpath(xpath)
                val fc2Id = elements.attr("content")
                logger.error {
                    """
                    获取 $fc2Id 发布日期失败
                    原因：无法匹配日期
                    当前日期的 html：$text
                    """.trimIndent()
                }
                ""
            } else {
                dateString
            }
        } catch (e: Exception) {
            logger.error(e) { "获取发布日期失败" }
            ""
        }
    }

    override fun getMagnetLinks(fc2Id: Long): List<MagnetLinks> {
        return sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)
    }
}
