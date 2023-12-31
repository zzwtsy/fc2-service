package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import okhttp3.*
import okhttp3.brotli.BrotliInterceptor
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpUtil {
    private val logger = KotlinLogging.logger { }
    private val client = OkHttpClient.Builder()
        .addInterceptor(BrotliInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .dispatcher(Dispatcher().apply { maxRequestsPerHost = 10 })
        .build()

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

    fun saveImages(imageNameToUrl: HashMap<String, String>) {
        imageNameToUrl.map { (filePathName, url) ->
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null) {
                        logger.error { "获取图片 $filePathName 失败" }
                        return
                    }
                    body.byteStream().let { bytes ->
                        val contentType = body.contentType()?.subtype
                        val outputStream = ByteArrayOutputStream()
                        Thumbnails.of(bytes).scale(1.0).outputQuality(0.81).toOutputStream(outputStream)

                        FileUtil.saveBinaryToFile(
                            outputStream.toByteArray(),
                            "${filePathName}.${contentType?.lowercase()}"
                        )
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    logger.error(e) { "获取图片 $filePathName 失败" }
                }
            })
        }
    }
}