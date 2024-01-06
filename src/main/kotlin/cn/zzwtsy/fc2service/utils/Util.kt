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
            "bloguid=4883e16a-8d23-4036-868a-9ec5622b90cb; fgcv=1%3BjzlTKJX7D%2FSEjmFFR4jTc9pfuiSSElq%2FdTBm2%2FuDXpX8Y82G; FC2_GDPR=true; __fc2id_rct=e7406592d81a869c26.30722883_2eb0793b36dd46967619153b0b35f8ea6f66e00cfd9d1e3e8dae02fe70cd912e; fcta=616dccb0e0be361a8f4d59e529e42aab04e3846a; aff=TXpZeU9UVXhNalE9; wei6H=1; CONTENTS_FC2_PHPSESSID=3c7abddcbd94246a25dc2430327ef743; contents_func_mode=sell; contents_mode=digital; language=cn; _pk_ref.1.623d=%5B%22%22%2C%22%22%2C1704375884%2C%22https%3A%2F%2Fwww.google.com%2F%22%5D; _pk_ses.1.623d=*; fcu=35777729-63b21f5e15c85c39c2889e924f623bce-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1704376770-141-6b18f198644ce5e447fc552d3f46449a; fcus=35777729-63b21f5e15c85c39c2889e924f623bce-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1704376770-141-6b18f198644ce5e447fc552d3f46449a; glgd_val=214033567%261f5b198dfeca8b0848dce0d07ed65638; _pk_id.1.623d=d7dee40671b6f696.1700314028.33.1704376782.1704375884.; fclo=1704376782573%2Czh-CN%2C8",
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