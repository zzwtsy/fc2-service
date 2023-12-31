package cn.zzwtsy.fc2service.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File

object FileUtil {
    private val logger = KotlinLogging.logger { }

    fun saveBinaryToFile(binaryData: ByteArray, filePath: String) {
        val file = File("/image/${filePath}")
        file.createNewFolder()
        file.outputStream().use {
            runCatching {
                it.write(binaryData)
            }.onFailure {
                logger.error(it) { "Failed to save file: $filePath" }
            }
        }
    }

    /**
     * 创建新文件夹
     */
    private fun File.createNewFolder() {
        logger.info { this.parent }
        val parentFile = this.parentFile
        parentFile?.mkdirs()
    }
}
