package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.api.RSSApi
import cn.zzwtsy.fc2service.dto.NyaaFc2VideoInfoDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class RSSService {
    companion object {
        private val FC2_ID_REGEX = "^FC2-PPV-\\d+\$".toRegex()
    }

    fun parseRSS() {
        val nyaaFc2VideoInfoList: MutableList<NyaaFc2VideoInfoDto> = mutableListOf()

        val rss = RSSApi().getRSS()
        rss.entries.forEach { item ->
            val title = item.title
            val infoHash = item.foreignMarkup.find { it.name == "infoHash" }?.text
            nyaaFc2VideoInfoList.add(NyaaFc2VideoInfoDto(getFc2Id(title), title, getMagnetLink(infoHash)))
        }
    }

    private fun getMagnetLink(hash: String?): String {
        if (hash.isNullOrEmpty() || hash.length != 40) return ""
        // magnet:?xt=urn:btih:f68c0375dac4d75822a3b3c0f436f32466f9f927
        return "magnet:?xt=urn:btih:$hash"
    }

    private fun getFc2Id(title: String?): String {
        if (title.isNullOrEmpty()) return ""
        val uppercase = title.uppercase(Locale.getDefault())
        val fc2Id = FC2_ID_REGEX.find(uppercase)?.value

        return fc2Id ?: ""
    }
}