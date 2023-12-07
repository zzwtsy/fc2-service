package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.entity.Tag
import cn.zzwtsy.fc2service.domain.schema.Tags
import org.ktorm.dsl.inList
import org.ktorm.support.mysql.bulkInsert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class TagsDao : BaseDao<Tag, Tags>(Tags) {
    @Autowired
    private lateinit var videoTagsDao: VideoTagsDao

    @Transactional
    fun insertAll(tags: Map<String, Int>) {
        if (tags.isEmpty()) return
        // 获取数据库中没有的 tag
        val findList = findList { it.tag inList tags.keys }
        val tagList = tags
            .filter { tag -> findList.none { it.tag == tag.key } }
            .toList()

        tagList.forEach { (tag, _) ->
            database.bulkInsert(Tags) {
                item {
                    set(it.tag, tag)
                }
            }
        }
        // 获取数据库中已经存在的 tag 的 id
        val tagIdList = findList {
            it.tag inList tags.keys
        }.associate { it.id to tags[it.tag]!! }
        videoTagsDao.insertAll(tagIdList)
    }
}