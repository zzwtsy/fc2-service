package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.repository.Fc2VideoInfoRepository
import cn.zzwtsy.fc2service.service.GetFc2VideoInfo
import cn.zzwtsy.fc2service.task.Fc2VideoTask
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Fc2ServiceApplicationTests {

    @Autowired
    lateinit var getFc2VideoInfo: GetFc2VideoInfo

    @Autowired
    private lateinit var fc2VideoInfoRepository: Fc2VideoInfoRepository

    @Autowired
    private lateinit var fc2VideoTask: Fc2VideoTask

    @Test
    fun contextLoads() {
        configureProxy()
        handleVideoInfo()
        // fc2VideoTask.executeGetVideoMagnetLinksTask()
    }

    // 设置系统代理
    private fun configureProxy() {
        System.setProperty("http.proxyHost", "127.0.0.1")
        System.setProperty("http.proxyPort", "10809")
        System.setProperty("https.proxyHost", "127.0.0.1")
        System.setProperty("https.proxyPort", "10809")
    }

    // 获取和保存视频信息
    private fun handleVideoInfo() {
        try {
            val fc2VideoInfo = getFc2VideoInfo.getFc2VideoInfo()
            fc2VideoInfoRepository.saveAll(fc2VideoInfo)
        } catch (e: Exception) {
            // 添加异常处理逻辑，例如打印异常信息或进行错误恢复
            println("发生异常: $e")
            // 可以根据实际需求选择是否要重新抛出异常或终止操作
            // throw e
        }
    }
}
