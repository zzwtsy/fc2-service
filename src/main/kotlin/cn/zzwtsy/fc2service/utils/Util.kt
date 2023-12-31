package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.Headers
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Util {
    private val logger = KotlinLogging.logger { }

    private val userAgent by lazy {
        listOf(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:108.0) Gecko/20100101 Firefox/108.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64; rv:108.0) Gecko/20100101 Firefox/108.0",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.2 Safari/605.1.15",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:108.0) Gecko/20100101 Firefox/108.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/109.0",
        )
    }

    fun isFc2Id(fc2ID: Long): Boolean {
        return if (fc2ID.toString().length != 7) {
            logger.warn { "Invalid FC2 ID: $fc2ID" }
            false
        } else {
            logger.debug { "Valid FC2 ID: $fc2ID" }
            true
        }
    }

    fun getFc2VideoPageHeaders(): Headers {
        return Headers.headersOf(
            "User-Agent", userAgent[Random().nextInt(userAgent.size)],
            "Cookie", "wei6H=1",
            "Host", "adult.contents.fc2.com",
        )
    }

    fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate {
        return LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    }

    fun getNowDate(): LocalDate {
        return LocalDate.now()
    }
}