package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.Fc2Api
import cn.zzwtsy.fc2service.domain.dto.Fc2VideoInfoDto
import cn.zzwtsy.fc2service.domain.schema.Fc2VideoBaseInfos.fc2Id
import cn.zzwtsy.fc2service.domain.schema.Fc2VideoBaseInfos.releaseDate
import cn.zzwtsy.fc2service.utils.HttpUtil
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
    private lateinit var fc2VideoInfoParseService: Fc2VideoInfoParseService

    suspend fun getFc2VideoInfo(): List<Fc2VideoInfoDto> {
        val fc2IdList = getFc2NewIdList()
        val list = hashSetOf<Fc2VideoInfoDto>()
        for (fc2Id in fc2IdList) {
            val fc2VideoInfoDto = fc2VideoInfoParseService.parse(fc2Id) ?: continue
            list.add(fc2VideoInfoDto)
        }

        val imageNameToUrl = hashMapOf<String, String>()
        list.forEach {
            val releaseDate = it.releaseDate.toString().replace("-", "/")
            imageNameToUrl["${releaseDate}/${it.fc2Id}/cover"] = it.cover
            imageNameToUrl.putAll(it.previewPictures.withIndex().associate { item ->
                ("${releaseDate}/${it.fc2Id}/picture_${item.index}" to item.value)
            })
        }

        HttpUtil.saveImages(imageNameToUrl)

        return list.toList()
    }

    private fun getFc2NewIdList(): List<Int> {
        // 获取最新的 FC2 个视频, 当获取视频页面为 null 时返回空 List
        val document = fc2Api.getFc2VideoPageHtmlByDescDate(2) ?: return emptyList()

        return document.body().selectXpath(itemXpath).asSequence().filter { element ->
            element.select(itemFc2IdSelector).attr("href").startsWith("/article")
        }.map { element ->
            val href = element.select(itemFc2IdSelector)
                // /article/4023931/
                .attr("href")
            numberRegex.find(href)?.value?.toInt() ?: 0
        }.distinct().filter { it != 0 }.toList()
    }
}