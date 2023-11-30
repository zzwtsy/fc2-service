package cn.zzwtsy.fc2service

import cn.zzwtsy.fc2service.dao.entity.*
import cn.zzwtsy.fc2service.dao.service.MovieService
import cn.zzwtsy.fc2service.service.GetFc2VideoInfo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class Fc2ServiceApplicationTests {
    @Autowired
    lateinit var movieService: MovieService
    @Autowired
    lateinit var getFc2VideoInfo: GetFc2VideoInfo

    @Test
    fun contextLoads() {
        getFc2VideoInfo.getFc2VideoInfo()

        // val movie = Movie().apply {
        //     id = 5
        //     this.title = "fc25"
        //     this.releaseDate = LocalDate.now()
        //     val item = this
        //     this.movieMagnetLinks =
        //         mutableSetOf(MovieMagnetLink().apply { this.magnetLink = "magnetLink5";this.movie = item })
        //     this.sellers = mutableSetOf(Seller().apply { this.name = "seller5" })
        //     this.tags = mutableSetOf(Tag().apply { this.tagName = "tag5" })
        //     this.previewPictures = mutableSetOf(PreviewPicture().apply { this.previewPicture = "previewPicture5" })
        // }
        // movieService.insert(movie)
    }
}


// fun main() {
//     val url = "https://storage91000.contents.fc2.com/file/297/29683723/1700643250.58.jpg"
//     // val url = "https://storage91000.contents.fc2.com/file/297/29683723/1700643250.22.gif"
//     val bytes = HttpUtil.sendGet(url).body?.bytes()!!
//     val toWebp = ImageConvertUtil.toWebp(bytes, 80)
//     val outputFile = File("gif75.webp")
//     outputFile.writeBytes(toWebp)
//     println("写入文件成功")
// }