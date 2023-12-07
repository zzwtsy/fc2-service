package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.dto.Fc2VideoInfoDto
import cn.zzwtsy.fc2service.domain.entity.Fc2VideoBaseInfo
import cn.zzwtsy.fc2service.domain.schema.Fc2VideoBaseInfos
import org.ktorm.dsl.inList
import org.ktorm.support.mysql.bulkInsert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class Fc2VideoBaseInfoDao : BaseDao<Fc2VideoBaseInfo, Fc2VideoBaseInfos>(Fc2VideoBaseInfos) {
    @Autowired
    private lateinit var magnetLinksDao: MagnetLinksDao

    @Autowired
    private lateinit var previewPicturesDao: PreviewPicturesDao

    @Autowired
    private lateinit var sellersDao: SellersDao

    @Autowired
    private lateinit var tagsDao: TagsDao

    @Autowired
    private lateinit var coverDao: CoverDao

    @Transactional
    fun insertAll(fc2VideoInfoDtos: List<Fc2VideoInfoDto>) {
        if (fc2VideoInfoDtos.isEmpty()) return

        // 获取待插入数据的 fc2Id 列表
        val fc2IdList = fc2VideoInfoDtos.map { it.fc2Id }

        // 查询已存在的 fc2Id
        val existingIds = findList { it.fc2Id inList fc2IdList }
            .map { it.fc2Id }
            .toHashSet()

        // 过滤掉已存在的数据
        val filteredList = fc2VideoInfoDtos.filterNot { it.fc2Id in existingIds }

        // 批量插入 Fc2VideoBaseInfos 数据
        if (filteredList.isNotEmpty()) {

            database.bulkInsert(Fc2VideoBaseInfos) {
                filteredList.forEach { item ->
                    item {
                        set(it.fc2Id, item.fc2Id)
                        set(it.title, item.title)
                        set(it.releaseDate, item.releaseDate)
                    }
                }
            }

            // 批量插入 magnetLinks 数据
            val magnetLinkData = filteredList.flatMap { fc2VideoInfo ->
                fc2VideoInfo.magnetLinks.map { it to fc2VideoInfo.fc2Id }
            }.toMap()
            magnetLinksDao.insertAll(magnetLinkData)

            // 批量插入 previewPictures 数据
            val previewPictureData = filteredList.flatMap { fc2VideoInfo ->
                fc2VideoInfo.previewPictures.map { it to fc2VideoInfo.fc2Id }
            }.toMap()
            previewPicturesDao.insertAll(previewPictureData)

            // 批量插入 sellers 数据
            val sellerData = filteredList.flatMap { fc2VideoInfo ->
                fc2VideoInfo.sellers.map { it to fc2VideoInfo.fc2Id }
            }.toMap()
            sellersDao.insertAll(sellerData)

            // 批量插入 tags 数据
            val tagData = filteredList.flatMap { fc2VideoInfo ->
                fc2VideoInfo.tags.map { it to fc2VideoInfo.fc2Id }
            }.toMap()
            tagsDao.insertAll(tagData)

            // 批量插入 cover 数据
            val coverData = filteredList.associate { fc2VideoInfo ->
                fc2VideoInfo.cover to fc2VideoInfo.fc2Id
            }
            coverDao.insertAll(coverData)
        }
    }
}