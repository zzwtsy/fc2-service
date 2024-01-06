package cn.zzwtsy.fc2service.api.model

import cn.zzwtsy.fc2service.utils.JsonUtil
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.Headers

data class Fc2ArticleRequestModel(
    @get:JsonProperty("Accept") @field:JsonProperty("Accept")
    val accept: String? = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",

    @get:JsonProperty("Accept-Encoding") @field:JsonProperty("Accept-Encoding")
    val acceptEncoding: String? = "gzip, deflate, br",

    @get:JsonProperty("Accept-Language") @field:JsonProperty("Accept-Language")
    val acceptLanguage: String? = "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",

    @get:JsonProperty("Cache-Control") @field:JsonProperty("Cache-Control")
    val cacheControl: String? = "no-cache",

    @get:JsonProperty("Connection") @field:JsonProperty("Connection")
    val connection: String? = "keep-alive",

    @get:JsonProperty("Cookie") @field:JsonProperty("Cookie")
    val cookie: String? = "bloguid=4883e16a-8d23-4036-868a-9ec5622b90cb; fgcv=1%3BjzlTKJX7D%2FSEjmFFR4jTc9pfuiSSElq%2FdTBm2%2FuDXpX8Y82G; FC2_GDPR=true; __fc2id_rct=e7406592d81a869c26.30722883_2eb0793b36dd46967619153b0b35f8ea6f66e00cfd9d1e3e8dae02fe70cd912e; fcta=616dccb0e0be361a8f4d59e529e42aab04e3846a; aff=TXpZeU9UVXhNalE9; glgd_val=214033567%261f5b198dfeca8b0848dce0d07ed65638; fclo=1704376802643%2Czh-CN%2C8; fcu=35777729-67b6a3752bf9dd6c5cb91b30c8451e6a-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1704516151-138-016f2edd753c4e0046e2ef8fa76eaa33; fcus=35777729-67b6a3752bf9dd6c5cb91b30c8451e6a-6f7ca7d8f8160762c3ef4fafc9770e1b-b9c84247-1704516151-138-016f2edd753c4e0046e2ef8fa76eaa33; contents_func_mode=sell; contents_mode=digital",

    @get:JsonProperty("DNT") @field:JsonProperty("DNT")
    val dnt: String? = "1",

    @get:JsonProperty("Host") @field:JsonProperty("Host")
    val host: String? = "adult.contents.fc2.com",

    @get:JsonProperty("Pragma") @field:JsonProperty("Pragma")
    val pragma: String? = "no-cache",

    @get:JsonProperty("sec-ch-ua") @field:JsonProperty("sec-ch-ua")
    val secChUa: String? = "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Microsoft Edge\";v=\"120\"",

    @get:JsonProperty("sec-ch-ua-mobile") @field:JsonProperty("sec-ch-ua-mobile")
    val secChUaMobile: String? = "?0",

    @get:JsonProperty("sec-ch-ua-platform") @field:JsonProperty("sec-ch-ua-platform")
    val secChUaPlatform: String? = "Windows",

    @get:JsonProperty("Sec-Fetch-Dest") @field:JsonProperty("Sec-Fetch-Dest")
    val secFetchDest: String? = "document",

    @get:JsonProperty("Sec-Fetch-Mode") @field:JsonProperty("Sec-Fetch-Mode")
    val secFetchMode: String? = "navigate",

    @get:JsonProperty("Sec-Fetch-Site") @field:JsonProperty("Sec-Fetch-Site")
    val secFetchSite: String? = "none",

    @get:JsonProperty("Sec-Fetch-User") @field:JsonProperty("Sec-Fetch-User")
    val secFetchUser: String? = "?1",

    @get:JsonProperty("Upgrade-Insecure-Requests") @field:JsonProperty("Upgrade-Insecure-Requests")
    val upgradeInsecureRequests: String? = "1",

    @get:JsonProperty("User-Agent") @field:JsonProperty("User-Agent")
    val userAgent: String? = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0"
) {

    fun toJson() = JsonUtil.toJson<Fc2ArticleRequestModel>()

    fun toOkHttp3Headers(): Headers {
        return Headers.headersOf(
            "Accept", "$accept",
            // "Accept-Encoding", "$acceptEncoding",
            "Accept-Language", "$acceptLanguage",
            "Cache-Control", "$cacheControl",
            "Connection", "$connection",
            "Cookie", "$cookie",
            "DNT", "$dnt",
            "Host", "$host",
            "Pragma", "$pragma",
            "sec-ch-ua", "$secChUa",
            "sec-ch-ua-mobile", "$secChUaMobile",
            "sec-ch-ua-platform", "$secChUaPlatform",
            "Sec-Fetch-Dest", "$secFetchDest",
            "Sec-Fetch-Mode", "$secFetchMode",
            "Sec-Fetch-Site", "$secFetchSite",
            "Sec-Fetch-User", "$secFetchUser",
            "Upgrade-Insecure-Requests", "$upgradeInsecureRequests",
            "User-Agent", "$userAgent"
        )
    }

    companion object {
        fun fromJson(json: String) = JsonUtil.fromJson<Fc2ArticleRequestModel>(json)
    }
}
