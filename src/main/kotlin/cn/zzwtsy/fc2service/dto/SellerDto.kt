package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.domain.modle.Seller
import java.io.Serializable

/**
 * DTO for {@link cn.zzwtsy.fc2service.domain.modle.Seller}
 */
data class SellerDto(var name: String? = null) : Serializable {
    fun toEntity(): Seller {
        val seller = Seller()
        seller.name = this.name
        return seller
    }
}