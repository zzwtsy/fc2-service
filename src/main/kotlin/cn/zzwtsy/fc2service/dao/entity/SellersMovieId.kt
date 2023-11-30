package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import org.hibernate.annotations.Comment
import java.io.Serializable
import java.util.*

@Embeddable
open class SellersMovieId : Serializable {
    @Comment("导演ID")
    @Column(name = "seller_id", nullable = false)
    open var sellerId: Int? = null

    @Comment("FC2 视频ID")
    @Column(name = "fc2_id", nullable = false)
    open var fc2Id: Int? = null
    override fun hashCode(): Int = Objects.hash(sellerId, fc2Id)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as SellersMovieId

        return sellerId == other.sellerId &&
                fc2Id == other.fc2Id
    }

    companion object {
        private const val serialVersionUID = 6318894342330580525L
    }
}