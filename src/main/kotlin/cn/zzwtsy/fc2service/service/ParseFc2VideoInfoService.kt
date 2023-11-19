package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.Fc2Api
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ParseFc2VideoInfoService {
    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var rssService: RSSService

    @Qualifier("fc2ArticleHTMLParseServiceImpl")
    @Autowired
    private lateinit var fc2ArticleHTMLParseService: Fc2ArticleHTMLParseService

    @Qualifier("fc2HubArticleHTMLParseServiceImpl")
    @Autowired
    private lateinit var fc2HubArticleHTMLParseService: Fc2ArticleHTMLParseService

    @Autowired
    lateinit var fc2Api: Fc2Api
    fun parse(): String {
        val movies: MutableList<String> = mutableListOf()
        val nyaaFc2VideoInfoList = rssService.parseRSS()
        for (fc2VideoInfoDto in nyaaFc2VideoInfoList) {
            var html = fc2Api.getFc2VideoPageHtml(fc2VideoInfoDto.fc2Id)
            if (html != null) {
                val parse = fc2ArticleHTMLParseService.parse(html)
            }
            html = fc2Api.getFc2HubVideoPageHtml(fc2VideoInfoDto.fc2Id)
            if (html != null) {
                val parse = fc2HubArticleHTMLParseService.parse(html)
            }

            logger.error { "获取 ${fc2VideoInfoDto.fc2Id} 视频信息失败" }
        }
        return ""
    }

}