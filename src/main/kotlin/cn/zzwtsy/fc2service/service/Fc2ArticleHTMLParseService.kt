package cn.zzwtsy.fc2service.service

import org.jsoup.nodes.Document

interface Fc2ArticleHTMLParseService {
    fun parse(html: Document?): String
}