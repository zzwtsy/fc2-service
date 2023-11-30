package cn.zzwtsy.fc2service.service

import cn.zzwtsy.fc2service.dto.Fc2VideoInfoDto
import org.jsoup.nodes.Document

interface Fc2ArticleHTMLParseService {
    fun parse(fc2Id: String): Fc2VideoInfoDto?

    /**
     * 获取标题
     * @param [html] html
     * @return [String]
     */
    fun getTitle(html: Document): String

    /**
     * 获取标签
     * @param [html] html
     * @return [List<String>]
     */
    fun getTags(html: Document): List<String>

    /**
     * 获取卖家
     * @param [html] html
     * @return [String]
     */
    fun getSeller(html: Document): String

    /**
     * 获取封面
     * @param [html] html
     * @return [String]
     */
    fun getCover(html: Document): String

    /**
     * 获取预览图片
     * @param [html] html
     * @return [List<String>]
     */
    fun getPreviewPictures(html: Document): List<String>

    /**
     * 获取发布日期
     * @param [html] html
     * @return [String]
     */
    fun getReleaseDate(html: Document): String
}