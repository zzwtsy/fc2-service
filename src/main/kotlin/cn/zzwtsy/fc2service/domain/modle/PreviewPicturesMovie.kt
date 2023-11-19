package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("演员- FC2 视频关联表，存储演员和他们出演的 FC2 视频之间的关系")
@Entity(name = "Preview_Pictures_Movie")
@Table(
    name = "preview_pictures_movie", schema = "fc2_service", indexes = [
        Index(name = "fc2Id", columnList = "fc2_id")
    ]
)
open class PreviewPicturesMovie {
    @EmbeddedId
    open var id: PreviewPicturesMovieId? = null

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("演员ID")
    @JoinColumn(name = "preview_pictures_id", nullable = false)
    open var previewPicturesId: PreviewPicture? = null

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("FC2 视频ID")
    @JoinColumn(name = "fc2_id", nullable = false)
    open var fc2Id: Movie? = null
}