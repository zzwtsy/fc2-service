package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("FC2 视频-标签关联表，存储 FC2 视频和它们对应的标签之间的关系")
@Entity(name = "Movie_Tag")
@Table(
    name = "movie_tags", schema = "fc2_service", indexes = [
        Index(name = "tagId", columnList = "tag_id")
    ]
)
open class MovieTag {
    @EmbeddedId
    open var id: MovieTagId? = null

    @MapsId("fc2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("FC2 视频ID")
    @JoinColumn(name = "fc2_id", nullable = false)
    open var fc2Id: Movie? = null

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("标签ID")
    @JoinColumn(name = "tag_id", nullable = false)
    open var tagId: Tag? = null
}