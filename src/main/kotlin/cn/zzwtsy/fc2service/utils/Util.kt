package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.Headers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Util {
    private val logger = KotlinLogging.logger { }

    fun getFc2VideoPageHeaders(): Headers {
        return Headers.headersOf(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0",
            "Cookie",
            "bloguid=4883e16a-8d23-4036-868a-9ec5622b90cb; fgcv=1%3BjzlTKJX7D%2FSEjmFFR4jTc9pfuiSSElq%2FdTBm2%2FuDXpX8Y82G; wei6H=1; FC2_GDPR=true; __fc2id_rct=e7406592d81a869c26.30722883_2eb0793b36dd46967619153b0b35f8ea6f66e00cfd9d1e3e8dae02fe70cd912e; fcta=616dccb0e0be361a8f4d59e529e42aab04e3846a; contents_func_mode=sell; CONTENTS_FC2_PHPSESSID=b0fbd8e22975fc27ac2ea4bd7c280b4a; fcu=35777729-ce74a35b52ff66c0223fb9ab4e1a664e-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1704196548-170-159323d9b5470ad930513bdc9dd8a6d4; fcus=35777729-ce74a35b52ff66c0223fb9ab4e1a664e-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1704196548-170-159323d9b5470ad930513bdc9dd8a6d4; glgd_val=214033567%261f5b198dfeca8b0848dce0d07ed65638; aff=TXpZeU9UVXhNalE9; contents_mode=digital; fclo=1704196558076%2Czh-CN%2C8; _pk_ref.1.623d=%5B%22%22%2C%22%22%2C1704196559%2C%22https%3A%2F%2Ffc2.com%2F%22%5D; _pk_id.1.623d=d7dee40671b6f696.1700314028.28.1704196559.1704196559.; _pk_ses.1.623d=*",
            "Host",
            "adult.contents.fc2.com",
        )
    }

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
}