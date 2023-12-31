package cn.zzwtsy.fc2service.controller

import cn.zzwtsy.fc2service.dto.R
import cn.zzwtsy.fc2service.enum.RCode
import cn.zzwtsy.fc2service.model.VideoInfo
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.utils.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Fc2VideoController {
    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @GetMapping("/getNewVideoInfoByDateDesc")
    fun getNewVideoInfoByDateDesc(
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int
    ): R<List<VideoInfo>> {
        if (pageNumber < 0 || pageSize < 1) {
            return R.failure(RCode.PARAM_ERROR)
        }
        val page = fc2VideoInfoRepository.queryVideoInfoByOrderByReleaseDateDesc(pageNumber, pageSize)
        if (page.content.isEmpty()) {
            return R.failure(RCode.NO_DATA)
        }
        return R.success(page.content, page.totalPages)
    }

    @GetMapping("/getVideoInfoById")
    fun getVideoInfoById(@RequestParam fc2Id: Long): R<VideoInfo> {
        if (!Util.isFc2Id(fc2Id)) {
            return R.failure(RCode.PARAM_ERROR)
        }
        val findByVideoId = fc2VideoInfoRepository.findByVideoId(fc2Id)
        return if (findByVideoId != null) {
            R.success(findByVideoId)
        } else {
            R.failure(RCode.NO_DATA)
        }
    }
}