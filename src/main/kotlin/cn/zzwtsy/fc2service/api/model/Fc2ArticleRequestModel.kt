package cn.zzwtsy.fc2service.api.model

import cn.zzwtsy.fc2service.utils.FileUtil
import cn.zzwtsy.fc2service.utils.JsonUtil
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.Headers
import java.io.File

data class Fc2ArticleRequestModel(
    @get:JsonProperty("Accept") @field:JsonProperty("Accept")
    val accept: String = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",

    @get:JsonProperty("Accept-Encoding") @field:JsonProperty("Accept-Encoding")
    val acceptEncoding: String = "gzip, deflate, br",

    @get:JsonProperty("Accept-Language") @field:JsonProperty("Accept-Language")
    val acceptLanguage: String = "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",

    @get:JsonProperty("Cache-Control") @field:JsonProperty("Cache-Control")
    val cacheControl: String = "no-cache",

    @get:JsonProperty("Connection") @field:JsonProperty("Connection")
    val connection: String = "keep-alive",

    @get:JsonProperty("Cookie") @field:JsonProperty("Cookie")
    var cookie: String = "bloguid=4883e16a-8d23-4036-868a-9ec5622b90cb; fgcv=1%3BjzlTKJX7D%2FSEjmFFR4jTc9pfuiSSElq%2FdTBm2%2FuDXpX8Y82G; FC2_GDPR=true; __fc2id_rct=e7406592d81a869c26.30722883_2eb0793b36dd46967619153b0b35f8ea6f66e00cfd9d1e3e8dae02fe70cd912e; fcta=616dccb0e0be361a8f4d59e529e42aab04e3846a; aff=TXpZeU9UVXhNalE9; wei6H=1; _ga=GA1.1.28295985.1705318548; _ga_3L3781JR57=GS1.1.1705824082.2.1.1705824248.0.0.0; contents_func_mode=sell; CONTENTS_FC2_PHPSESSID=7999060d00d4088879993a54ee685b0a; fcu=35777729-0d552ad5a0c91d9948e9caf3c75f3b8d-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1706070526-43-dd8a5947b4f77ec19e1f14eb51234daa; fcus=35777729-0d552ad5a0c91d9948e9caf3c75f3b8d-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1706070526-43-dd8a5947b4f77ec19e1f14eb51234daa; contents_mode=digital; _pk_ref.1.623d=%5B%22%22%2C%22%22%2C1706097819%2C%22https%3A%2F%2Fcontents.fc2.com%2F%22%5D; _pk_ses.1.623d=*; fclo=1706097819398%2Czh-CN%2C8; _pk_id.1.623d=d7dee40671b6f696.1700314028.58.1706097824.1706097819.",

    @get:JsonProperty("DNT") @field:JsonProperty("DNT")
    val dnt: String = "1",

    @get:JsonProperty("Host") @field:JsonProperty("Host")
    val host: String = "adult.contents.fc2.com",

    @get:JsonProperty("Pragma") @field:JsonProperty("Pragma")
    val pragma: String = "no-cache",

    @get:JsonProperty("sec-ch-ua") @field:JsonProperty("sec-ch-ua")
    val secChUa: String = "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Microsoft Edge\";v=\"120\"",

    @get:JsonProperty("sec-ch-ua-mobile") @field:JsonProperty("sec-ch-ua-mobile")
    val secChUaMobile: String = "?0",

    @get:JsonProperty("sec-ch-ua-platform") @field:JsonProperty("sec-ch-ua-platform")
    val secChUaPlatform: String = "Windows",

    @get:JsonProperty("Sec-Fetch-Dest") @field:JsonProperty("Sec-Fetch-Dest")
    val secFetchDest: String = "document",

    @get:JsonProperty("Sec-Fetch-Mode") @field:JsonProperty("Sec-Fetch-Mode")
    val secFetchMode: String = "navigate",

    @get:JsonProperty("Sec-Fetch-Site") @field:JsonProperty("Sec-Fetch-Site")
    val secFetchSite: String = "none",

    @get:JsonProperty("Sec-Fetch-User") @field:JsonProperty("Sec-Fetch-User")
    val secFetchUser: String = "?1",

    @get:JsonProperty("Upgrade-Insecure-Requests") @field:JsonProperty("Upgrade-Insecure-Requests")
    val upgradeInsecureRequests: String = "1",

    @get:JsonProperty("User-Agent") @field:JsonProperty("User-Agent")
    val userAgent: String = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0"
) {
    fun toJson() = JsonUtil.toJson(this)

    fun toOkHttp3Headers(): Headers {
        return Headers.headersOf(
            "Accept", accept,
            // "Accept-Encoding", "$acceptEncoding",
            "Accept-Language", acceptLanguage,
            "Cache-Control", cacheControl,
            "Connection", connection,
            "Cookie", cookie,
            "DNT", dnt,
            "Host", host,
            "Pragma", pragma,
            "sec-ch-ua", secChUa,
            "sec-ch-ua-mobile", secChUaMobile,
            "sec-ch-ua-platform", secChUaPlatform,
            "Sec-Fetch-Dest", secFetchDest,
            "Sec-Fetch-Mode", secFetchMode,
            "Sec-Fetch-Site", secFetchSite,
            "Sec-Fetch-User", secFetchUser,
            "Upgrade-Insecure-Requests", upgradeInsecureRequests,
            "User-Agent", userAgent
        )
    }


    companion object {
        fun fromJson(): Fc2ArticleRequestModel {
            if (!File("./config/header.json").exists()) {
                FileUtil.saveTextToFile(JsonUtil.toJson(Fc2ArticleRequestModel()), "./config/header.json")
            }
            val text = File("./config/header.json").readText()
            return JsonUtil.fromJson<Fc2ArticleRequestModel>(text)
        }
    }
}
