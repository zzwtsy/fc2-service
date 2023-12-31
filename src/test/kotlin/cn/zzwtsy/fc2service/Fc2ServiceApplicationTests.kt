package cn.zzwtsy.fc2service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class Fc2ServiceApplicationTests {

    // @Autowired
    // lateinit var getFc2VideoInfo: GetFc2VideoInfo

    // @Autowired
    // private lateinit var fc2VideoBaseInfoDao: Fc2VideoBaseInfoDao

    @Test
    fun contextLoads() {
        // val fc2VideoMagnetLinks = sukebeiNyaaHTMLParseService.getFc2VideoMagnetLinks("2763672")
        // var fc2VideoInfo: List<Fc2VideoInfoDto>
        // runBlocking {
        //     val measureTimeMillis1 = measureTimeMillis { fc2VideoInfo = getFc2VideoInfo.getFc2VideoInfo() }
        //     println("get data from api 耗时: $measureTimeMillis1 毫秒")
        //     val measureTimeMillis = measureTimeMillis { fc2VideoBaseInfoDao.insertAll(fc2VideoInfo) }
        //     println("insert data to db 耗时: $measureTimeMillis 毫秒")
        // }
    }
}

fun main() {
    val date = LocalDate.parse("2023-12-09")
    println(date.isAfter(LocalDate.now()))
}