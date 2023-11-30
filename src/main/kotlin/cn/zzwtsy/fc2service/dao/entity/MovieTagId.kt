package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import org.hibernate.annotations.Comment
import java.io.Serializable
import java.util.*

@Embeddable
open class MovieTagId : Serializable {
    @Comment("FC2 视频ID")
    @Column(name = "fc2_id", nullable = false)
    open var fc2Id: Int? = null

    @Comment("标签ID")
    @Column(name = "tag_id", nullable = false)
    open var tagId: Int? = null
    override fun hashCode(): Int = Objects.hash(fc2Id, tagId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as MovieTagId

        return fc2Id == other.fc2Id &&
                tagId == other.tagId
    }

    companion object {
        private const val serialVersionUID = 5246156299321409492L
    }
}