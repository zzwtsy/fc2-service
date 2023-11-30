package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.Fc2Api
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetFc2VideoInfo {
    private val logger = KotlinLogging.logger { }

    private val itemXpath = "//*[@id=\"pjx-container\"]/div[2]/div[2]/section[2]/div[@class=\"c-cntCard-110-f\"]"
    private val itemFc2IdSelector = ".c-cntCard-110-f_thumb > a"
    private val numberRegex = "\\d+".toRegex()

    @Autowired
    private lateinit var fc2Api: Fc2Api

    @Autowired
    private lateinit var sukebeiNyaaHTMLParseService: SukebeiNyaaHTMLParseService

    @Autowired
    private lateinit var fc2ArticleHTMLParseService: Fc2ArticleHTMLParseService

    fun getFc2VideoInfo() {
        val fc2IdList = getFc2NewIdList()
        for (fc2Id in fc2IdList) {
            val magnetLinks = sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks(fc2Id)
            val fc2VideoInfoDto = fc2ArticleHTMLParseService.parse(fc2Id)
            println(
                """
                   fc2Id: $fc2Id, 
                   magnetLinks: $magnetLinks
                   fc2VideoInfoDto: $fc2VideoInfoDto
                """.trimMargin()
            )
        }
    }

    private fun getFc2NewIdList(): List<String> {
        // 获取最新的 FC2 个视频, 当获取视频页面为 null 时返回空 List
        val document = fc2Api.getFc2VideoPageHtmlByDescDate() ?: return emptyList()

        return document.body()
            .selectXpath(itemXpath)
            .asSequence()
            .filter { element ->
                element.select(itemFc2IdSelector).attr("href").startsWith("/article")
            }.map { element ->
                val href = element.select(itemFc2IdSelector)
                    // /article/4023931/
                    .attr("href")
                numberRegex.find(href)?.value ?: ""
            }.distinct()
            .filter { it.isNotBlank() || it.isNotEmpty() }
            .toList()
    }
}