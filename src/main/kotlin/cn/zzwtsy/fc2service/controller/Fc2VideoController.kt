package cn.zzwtsy.fc2service.controller

import cn.zzwtsy.fc2service.dto.R
import cn.zzwtsy.fc2service.enums.RCode
import cn.zzwtsy.fc2service.model.VideoInfo
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/frontendAPI")
class Fc2VideoController {
    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @GetMapping("/getNewVideoInfoListByDateDesc")
    fun getNewVideoInfoByDateDesc(
        @RequestParam(required = true) pageIndex: Int,
        @RequestParam(required = true) pageSize: Int
    ): R<List<VideoInfo>> {
        if (pageIndex < 0 || pageSize < 1) {
            return R.failure(RCode.PARAM_ERROR)
        }
        val videoInfoPage = fc2VideoInfoRepository.queryVideoInfoListOrderByReleaseDateDesc(pageIndex, pageSize)
        return if (videoInfoPage.content.isEmpty()) {
            R.failure(RCode.NO_DATA)
        } else {
            R.success(videoInfoPage.content, videoInfoPage.totalPages)
        }
    }

    @GetMapping("/getVideoInfoById")
    fun getVideoInfoById(@RequestParam(required = true) fc2Id: Long): R<VideoInfo> {
        val videoInfo = fc2VideoInfoRepository.findVideoInfoByVideoId(fc2Id)
        return if (videoInfo != null) {
            R.success(videoInfo)
        } else {
            R.failure(RCode.NO_DATA)
        }
    }

    @GetMapping("/getVideoInfoListByTagId")
    fun getVideoInfoListByTagId(
        @RequestParam(required = true) tagId: Long,
        @RequestParam(required = true) pageIndex: Int,
        @RequestParam(required = true) pageSize: Int
    ): R<List<VideoInfo>> {
        if (pageIndex < 0 || pageSize < 1) {
            return R.failure(RCode.PARAM_ERROR)
        }
        val videoInfoPage = fc2VideoInfoRepository.queryVideoInfoListByTagsId(tagId, pageIndex, pageSize)
        return if (videoInfoPage.content.isEmpty()) {
            R.failure(RCode.NO_DATA)
        } else {
            R.success(videoInfoPage.content, videoInfoPage.totalPages)
        }
    }

    @GetMapping("/getVideoInfoListBySellerId")
    fun getVideoInfoListBySellerId(
        @RequestParam(required = true) sellerId: Long,
        @RequestParam(required = true) pageIndex: Int,
        @RequestParam(required = true) pageSize: Int
    ): R<List<VideoInfo>> {
        if (pageIndex < 0 || pageSize < 1) {
            return R.failure(RCode.PARAM_ERROR)
        }
        val videoInfoPage = fc2VideoInfoRepository.queryVideoInfoByListBySellerId(sellerId, pageIndex, pageSize)
        return if (videoInfoPage.content.isEmpty()) {
            R.failure(RCode.NO_DATA)
        } else {
            R.success(videoInfoPage.content, videoInfoPage.totalPages)
        }
    }
}