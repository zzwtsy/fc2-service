package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.SukebeiNyaaApi
import cn.zzwtsy.fc2service.model.MagnetLinks
import cn.zzwtsy.fc2service.model.by
import io.github.oshai.kotlinlogging.KotlinLogging
import org.babyfish.jimmer.kt.new
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

    /**
     * 获取指定 fc2Id 视频的磁力链接列表
     *
     * @param fc2Id
     * @return 磁力链接列表
     */
    fun getFc2VideoMagnetLinks(fc2Id: Long): List<MagnetLinks> {
        logger.info { "开始获取 $fc2Id 视频的磁力链接" }
        val document = sukebeiNyaaApi.searchByFc2Id(fc2Id) ?: return emptyList()

        // 判断当前 fc2 视频是否有磁力链接
        val noResultsFound = document.selectXpath(noResultsFoundXpath)
        if (noResultsFound.isNotEmpty()) return emptyList()

        return document.selectXpath(itemsXpath).mapNotNull {
            val fileSize = it.selectXpath("td[4]").text()
            val isSubmitterTrusted = it.hasClass("success")

            val attr = it.selectXpath("td[3]/a[2]").attr("href")
            val link = magnetLinkRegex.find(attr)?.value

            if (link.isNullOrBlank()) {
                logger.warn { "$fc2Id 找不到磁力链接: $attr" }
                return@mapNotNull null
            }

            new(MagnetLinks::class).by {
                this.link = link
                this.fileSize = fileSize
                this.isSubmitterTrusted = isSubmitterTrusted
            }
        }
    }
}