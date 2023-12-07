package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.entity.Cover
import cn.zzwtsy.fc2service.domain.schema.Covers
import io.github.oshai.kotlinlogging.KotlinLogging
import org.ktorm.dsl.inList
import org.ktorm.support.mysql.bulkInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class CoverDao : BaseDao<Cover, Covers>(Covers) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun insertAll(coverUrlFc2Id: Map<String, Int>) {
        if (coverUrlFc2Id.isEmpty()) return
        val findOne = findList {
            it.coverUrl inList coverUrlFc2Id.keys
        }.map { it.coverUrl }

        val coverUrlList = coverUrlFc2Id
            .filter { (coverUrl, _) -> findOne.none { it == coverUrl } }
            .toMap()

        database.bulkInsert(Covers) {
            coverUrlList.forEach { (coverUrl, fc2Id) ->
                item {
                    set(it.fc2VideoBaseInfoFc2Id, fc2Id)
                    set(it.coverUrl, coverUrl)
                }
            }
        }
    }
}