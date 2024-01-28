package cn.zzwtsy.fc2service.interceptor

import cn.zzwtsy.fc2service.utils.Fc2Headers
import cn.zzwtsy.fc2service.utils.FileUtil
import cn.zzwtsy.fc2service.utils.JsonUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.Interceptor
import okhttp3.Response

object UpdateFc2CookieInterceptor : Interceptor {
    private val logger = KotlinLogging.logger { }
    override fun intercept(chain: Interceptor.Chain): Response {
        val host = chain.request().url.host
        if (host.endsWith("fc2.com")) {
            val newCookie = chain.request().header("Cookie")
            val oldCookie = Fc2Headers.cookie
            if (newCookie != null && !cookieEquals(newCookie, oldCookie)) {
                logger.info { "更新 FC2 Cookie" }
                Fc2Headers.cookie = newCookie
                FileUtil.saveTextToFile(JsonUtil.toJson(Fc2Headers), "./config/header.json")
                val request = chain.request().newBuilder()
                    .header("Cookie", newCookie)
                    .build()
                return chain.proceed(request)
            }
        }
        return chain.proceed(chain.request())
    }

    private fun String.splitCookie(): List<String> {
        return this.split(";")
    }

    private fun cookieEquals(cookie1: String, cookie2: String): Boolean {
        return cookie1.splitCookie().all { cookie2.contains(it) }
    }
}