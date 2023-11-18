package cn.zzwtsy.fc2service.api

import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import java.net.URL


class RSSApi {
    companion object {
        private const val SUKEBEI_NYAA_RSS_URL = "https://sukebei.nyaa.si/?page=rss&q=FC2&c=2_2&f=2"
    }

    fun getRSS(): SyndFeed {
        return SyndFeedInput().build(XmlReader(URL(SUKEBEI_NYAA_RSS_URL)))
    }
}