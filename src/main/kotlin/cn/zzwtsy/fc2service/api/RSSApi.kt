package cn.zzwtsy.fc2service.api

import cn.zzwtsy.fc2service.utils.HttpUtil
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader

class RSSApi {
    companion object {
        private const val SUKEBEI_NYAA_RSS_URL = "https://sukebei.nyaa.si/?page=rss&q=FC2&c=2_2&f=2"
    }

    fun getRSS(): SyndFeed? {
        val response = HttpUtil.sendGet(SUKEBEI_NYAA_RSS_URL).body?.byteStream() ?: return null
        return SyndFeedInput().build(XmlReader(response))
    }
}