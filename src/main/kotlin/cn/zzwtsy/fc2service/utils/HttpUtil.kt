package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpUtil {
    private val logger = KotlinLogging.logger { }
    private val client = OkHttpClient.Builder()
        // .addInterceptor(BrotliInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .dispatcher(Dispatcher().apply { maxRequestsPerHost = 5 })
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
        val builder = Request.Builder().url(url)
        if (headers != null) builder.headers(headers)
        val request = builder.build()

        // 让请求加入到队列中并指定回调函数
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // 非成功响应则记录错误日志
                if (!response.isSuccessful) {
                    logger.error { "获取图片 $filePathName 失败, 响应状态码: ${response.code}" }
                    return
                }

                // 获取响应体
                val body = response.body
                if (body == null) {
                    // 无响应体则记录错误日志
                    logger.error { "获取图片 $filePathName 失败, 无响应体" }
                    return
                }

                // 保存图片
                saveImage(body, filePathName)
            }

            override fun onFailure(call: Call, e: IOException) {
                // 记录下载图片失败的错误日志
                logger.error(e) { "获取图片 $filePathName 失败" }
            }
        })

        // 定阅shutdown以确保所有请求已经处理
        client.dispatcher.executorService.shutdown()
    }

    private fun saveImage(body: ResponseBody, filePathName: String) {
        // 获取内容类型
        val contentType = body.contentType()?.subtype
        if (contentType == null) {
            // 无法获取内容类型则记录错误日志
            logger.error { "获取图片 $filePathName 失败, 无法获取内容类型" }
            return
        }

        // 创建一个输出流
        val outputStream = ByteArrayOutputStream()

        // 调整图片大小并设置图片质量后将图片输出到输出流中
        Thumbnails.of(body.byteStream()).scale(1.0).outputQuality(0.75).toOutputStream(outputStream)

        // 使用输出流
        outputStream.use {
            // 保存图片到文件中
            val fullFilePathName = "${filePathName}.${contentType.lowercase()}"
            FileUtil.saveBinaryToFile(it.toByteArray(), fullFilePathName)
        }
    }

}