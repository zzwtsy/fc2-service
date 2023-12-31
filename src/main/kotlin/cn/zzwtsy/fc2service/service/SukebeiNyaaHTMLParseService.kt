package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.SukebeiNyaaApi
import cn.zzwtsy.fc2service.dto.MagnetLinksDto
import cn.zzwtsy.fc2service.utils.Util
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SukebeiNyaaHTMLParseService {
    private val logger = KotlinLogging.logger { }

    private val itemsXpath = "/html/body/div/div[2]/table/tbody/tr"
    private val noResultsFoundXpath = "/html/body/div/h3"
    private val magnetLinkRegex = Regex("magnet:\\?xt=urn:btih:[a-zA-Z0-9]+")

    @Autowired
    private lateinit var sukebeiNyaaApi: SukebeiNyaaApi

    fun getFc2VideoMagnetLinks(fc2Id: Long): List<MagnetLinksDto> {
        if (!Util.isFc2Id(fc2Id)) return emptyList()
        val document = sukebeiNyaaApi.searchByFc2Id(fc2Id) ?: return emptyList()

        // 判断当前 fc2 视频是否有磁力链接
        val noResultsFound = document.selectXpath(noResultsFoundXpath)
        if (noResultsFound.isNotEmpty()) return emptyList()

        val items = document.selectXpath(itemsXpath)
        val magnetLinks = mutableSetOf<MagnetLinksDto>()
        for (item in items) {
            val fileSize = item.selectXpath("td[4]").text()
            val isSubmitterTrusted = item.hasClass("success")

            val attr = item.selectXpath("td[3]/a[2]").attr("href")
            val link = magnetLinkRegex.find(attr)?.value
            if (link.isNullOrEmpty()) {
                logger.warn { "$fc2Id 找不到磁力链接:$link" }
                continue
            }

            magnetLinks.add(MagnetLinksDto(link, fileSize, isSubmitterTrusted))
        }

        return magnetLinks.toList()
    }
}