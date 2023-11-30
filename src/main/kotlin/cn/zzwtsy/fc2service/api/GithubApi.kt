package cn.zzwtsy.fc2service.api

import cn.zzwtsy.fc2service.utils.HttpUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.FormBody
import okhttp3.Headers.Companion.headersOf
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class GithubApi {
    private val logger = KotlinLogging.logger { }
    private val githubApiUrl = "https://api.github.com/repos/"
    private val accessToken = "YOUR_ACCESS_TOKEN"
    private val owner = "zzwtsy"
    private val repo = "image-repository"
    private val branch = "main"
    private val commitMessage = "upload image ${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}"

    /**
     * 上传文件
     */
    fun uploadFile(fileName: String, fileContent: ByteArray) {

        val apiUrl = "${githubApiUrl}${owner}/${repo}/contents/fc2service/${fileName}"
        val headers = headersOf(
            "Authorization", "token $accessToken",
            "Content-Type", "application/json"
        )
        val imageBase64 = Base64.getEncoder().encodeToString(fileContent)
        val body = FormBody.Builder()
            .add("message", commitMessage)
            .add("content", imageBase64)
            .add("branch", branch)
            .build()
        HttpUtil.sendPost(apiUrl, headers, body)
    }
}