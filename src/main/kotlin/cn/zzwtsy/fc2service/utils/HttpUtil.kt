package cn.zzwtsy.fc2service.utils

import okhttp3.*
import java.util.concurrent.TimeUnit

object HttpUtil {
    private val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build()

    /**
     * 发送 Get 请求
     * @param [url] url
     * @param [headers] 请求头
     * @return [Response]
     */
    fun sendGet(url: String, headers: Headers? = null): Response {
        val builder = Request.Builder().get().url(url)
        val request = if (headers != null) builder.headers(headers).build() else builder.build()
        return client.newCall(request).execute()
    }

    /**
     * 发送 Post 请求
     * @param [url] url
     * @param [headers] 请求头
     * @param [requestBody] 请求体
     * @return [Response]
     */
    fun sendPost(url: String, headers: Headers?, requestBody: RequestBody): Response {
        val builder = Request.Builder().post(requestBody).url(url)
        val request = if (headers != null) builder.headers(headers).build() else builder.build()
        return client.newCall(request).execute()
    }
}