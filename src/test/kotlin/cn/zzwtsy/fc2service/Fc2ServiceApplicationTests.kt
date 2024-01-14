package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.api.model.Fc2ArticleRequestModel
import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.service.Fc2VideoInfoService
import cn.zzwtsy.fc2service.task.Fc2VideoTask
import cn.zzwtsy.fc2service.utils.HttpUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

@SpringBootTest
class Fc2ServiceApplicationTests {

    @Autowired
    private lateinit var fc2VideoInfoService: Fc2VideoInfoService

    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @Autowired
    private lateinit var fc2VideoTask: Fc2VideoTask

    @Test
    fun contextLoads() = runBlocking {
        // configureProxy()
        // handleVideoInfo()
        fc2VideoTask.executeGetVideoMagnetLinksTask()
        // 延迟 10 分钟，以便测试任务执行完成
        // delay(300000 / 2)
    }


    // 获取和保存视频信息
    private fun handleVideoInfo() {
        try {
            val fc2VideoInfo = fc2VideoInfoService.getFc2VideoInfo()
            fc2VideoInfoRepository.saveAll(fc2VideoInfo)
        } catch (e: Exception) {
            // 添加异常处理逻辑，例如打印异常信息或进行错误恢复
            println("发生异常: $e")
            // 可以根据实际需求选择是否要重新抛出异常或终止操作
            // throw e
        }
    }
}
