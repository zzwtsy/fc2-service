package cn.zzwtsy.fc2service.domain.dao

import cn.zzwtsy.fc2service.domain.entity.Seller
import cn.zzwtsy.fc2service.domain.schema.Sellers
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.support.mysql.bulkInsert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class SellersDao : BaseDao<Seller, Sellers>(Sellers) {
    @Autowired
    private lateinit var videoSellersDao: VideoSellersDao

    fun insertAll(sellers: Map<String, Int>) {
        if (sellers.isEmpty()) return
        // 获取数据库中没有的 seller
        var sellerList = sellers
            .filter { (seller, _) -> this.findOne { it.seller eq seller } == null }
            .toMap()

        if (sellerList.isEmpty()) sellerList = sellers
        sellerList.filter { it.key.isNotEmpty() || it.key.isNotBlank() }
        sellerList.forEach { (seller, _) ->
            database.bulkInsert(Sellers) {
                item {
                    set(it.seller, seller)
                }
            }
        }
        // 获取数据库中已经存在的 seller 的 id
        val sellerIdList = findList {
            it.seller inList sellers.keys
        }.associate { it.id to sellers[it.seller]!! }
        // 插入 VideoSellers
        videoSellersDao.insertAll(sellerIdList)
    }
}