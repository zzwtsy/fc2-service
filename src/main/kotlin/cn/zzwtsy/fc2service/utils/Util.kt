package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Util {
    private val logger = KotlinLogging.logger { }

    /**
     * 将字符串转换为LocalDate对象
     *
     * @param pattern 日期格式，默认为"yyyy-MM-dd"
     * @return 转换后的LocalDate对象
     */
    fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate {
        return LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    }


    fun getNowDate(): LocalDate {
        return LocalDate.now()
    }

    fun compressImage(inputStream: InputStream, quality: Double = 0.75): OutputStream {
        val outputStream: OutputStream = OutputStream.nullOutputStream()
        Thumbnails.of(inputStream).scale(1.0).outputQuality(quality).toOutputStream(outputStream)
        return outputStream
    }
}