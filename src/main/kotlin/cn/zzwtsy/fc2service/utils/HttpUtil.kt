package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import okhttp3.*
import okhttp3.brotli.BrotliInterceptor
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

object HttpUtil {
    private val logger = KotlinLogging.logger { }
    private val client = OkHttpClient.Builder()
        .protocols(
            listOf(Protocol.HTTP_2, Protocol.HTTP_1_1)
        )
        .addInterceptor(BrotliInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .dispatcher(Dispatcher().apply {
            maxRequests = 5
            maxRequestsPerHost = 2
        })
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

    fun downloadImage(filePathName: String, url: String, headers: Headers? = null) {
        val builder = Request.Builder().get().url(url)
        val request = if (headers != null) builder.headers(headers).build() else builder.build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // 获取响应体
                val body = response.body
                if (body == null) {
                    response.close()
                    logger.error { "获取图片 $filePathName 失败, 无响应体" }
                    return
                }

                try {
                    // 非成功响应则记录错误日志
                    if (!response.isSuccessful) {
                        logger.error { "获取图片 $filePathName 失败, 响应状态码: ${response.code}" }
                        return
                    }

                    // 保存图片
                    val contentType = body.contentType()?.subtype
                    if (contentType == null) {
                        // 无法获取内容类型则记录错误日志
                        logger.error { "获取图片 $filePathName 失败, 无法获取内容类型" }
                        return
                    }
                    saveImage(body.byteStream(), contentType, filePathName)
                } finally {
                    response.close()
                    body.close()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                // 记录下载图片失败的错误日志
                logger.error(e) { "获取图片 $filePathName 失败" }
            }
        })
    }

    private fun saveImage(inputBytes: InputStream, contentType: String, filePathName: String) {
        val outputStream = ByteArrayOutputStream()

        when (contentType) {
            "png", "PNG" -> {
                Thumbnails.of(inputBytes).scale(1.0).outputFormat("JPEG").outputQuality(0.75)
                    .toOutputStream(outputStream)
            }
            else -> {
                Thumbnails.of(inputBytes).scale(1.0).outputQuality(0.75).toOutputStream(outputStream)
            }
        }

        // 使用输出流
        outputStream.use { outputBytes ->
            // 保存图片到文件
            val fullFilePathName = when (contentType) {
                "png", "PNG" -> "${filePathName}.jpeg"
                else -> "${filePathName}.${contentType.lowercase()}"
            }
            FileUtil.saveBinaryToFile(outputBytes.toByteArray(), fullFilePathName)
        }
    }
}