package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import org.hibernate.annotations.Comment
import java.io.Serializable
import java.util.*

@Embeddable
open class PreviewPicturesMovieId : Serializable {
    @Comment("演员ID")
    @Column(name = "preview_pictures_id", nullable = false)
    open var previewPicturesId: Int? = null

    @Comment("FC2 视频ID")
    @Column(name = "fc2_id", nullable = false)
    open var fc2Id: Int? = null
    override fun hashCode(): Int = Objects.hash(previewPicturesId, fc2Id)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as PreviewPicturesMovieId

        return previewPicturesId == other.previewPicturesId &&
                fc2Id == other.fc2Id
    }

    companion object {
        private const val serialVersionUID = -33191466201182899L
    }
}