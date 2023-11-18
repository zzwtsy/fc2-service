package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class MovieTagId : Serializable {
    @Column(name = "fc2Id", nullable = false)
    open var fc2Id: Int? = null

    @Column(name = "tagId", nullable = false)
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
        private const val serialVersionUID = 398230684016654559L
    }
}