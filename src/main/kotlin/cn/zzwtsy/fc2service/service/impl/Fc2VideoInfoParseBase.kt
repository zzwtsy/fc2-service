package cn.zzwtsy.fc2service.service.impl

import cn.zzwtsy.fc2service.model.*
import cn.zzwtsy.fc2service.service.Fc2VideoInfoParseService
import org.jsoup.nodes.Document

abstract class Fc2VideoInfoParseBase : Fc2VideoInfoParseService {
    /**
     * 获取标题
     * @param [html] html
     * @return [String]
     */
    protected abstract fun getTitle(html: Document): String

    /**
     * 获取标签
     * @param [html] html
     * @return [List<String>]
     */
    protected abstract fun getTags(html: Document): List<Tags>

    /**
     * 获取卖家
     * @param [html] html
     * @return [String]
     */
    protected abstract fun getSeller(html: Document): List<Sellers>

    /**
     * 获取封面
     * @param [html] html
     * @return [String]
     */
    protected abstract fun getCover(html: Document): Covers?

    /**
     * 获取预览图片
     * @param [html] html
     * @return [List<String>]
     */
    protected abstract fun getPreviewPictures(html: Document): List<PreviewPictures>

    /**
     * 获取发布日期
     * @param [html] html
     * @return [String]
     */
    protected abstract fun getReleaseDate(html: Document): String

    /**
     * 获取磁力链接
     * @param [fc2Id] fc2 id
     * @return [List<String>]
     */
    protected abstract fun getMagnetLinks(fc2Id: Long): List<MagnetLinks>
}