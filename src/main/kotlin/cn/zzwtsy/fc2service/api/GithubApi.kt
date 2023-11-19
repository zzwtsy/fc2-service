package cn.zzwtsy.fc2service.api

import cn.zzwtsy.fc2service.utils.HttpUtil
import okhttp3.FormBody
import okhttp3.Headers.Companion.headersOf
import org.springframework.stereotype.Component
import java.util.*

@Component
class GithubApi {
    companion object {
        private const val GITHUB_API = "https://api.github.com/repos/"
        private const val ACCESS_TOKEN = "YOUR_ACCESS_TOKEN"
        private const val OWNER = "zzwtsy"
        private const val REPO = "image-repository"
        private const val BRANCH = "main"
        private const val COMMIT_MESSAGE = "Upload file"
    }

    /**
     * 上传文件
     */
    fun uploadFile(fileName: String, fileContent: ByteArray) {

        val apiUrl = "${GITHUB_API}${OWNER}/${REPO}/contents/fc2service/${fileName}"
        val headers = headersOf(
            "Authorization", "token $ACCESS_TOKEN",
            "Content-Type", "application/json"
        )
        val imageBase64 = Base64.getEncoder().encodeToString(fileContent)
        val body = FormBody.Builder()
            .add("message", COMMIT_MESSAGE)
            .add("content", imageBase64)
            .add("branch", BRANCH)
            .build()
        HttpUtil.sendPost(apiUrl, headers, body)
    }
}