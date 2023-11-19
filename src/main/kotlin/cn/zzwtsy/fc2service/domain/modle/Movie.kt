package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.Instant

@Comment("FC2 视频表，存储每部 FC2 视频的一般信息")
@Entity(name = "Movie")
@Table(name = "movies", schema = "fc2_service")
open class Movie {
    @Id
    @Comment("FC2 视频唯一标识ID")
    @Column(name = "fc2_id", nullable = false)
    open var id: Int? = null

    @Comment("FC2 视频标题")
    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Comment("上映日期")
    @Column(name = "release_date")
    open var releaseDate: Instant? = null

    @OneToMany(mappedBy = "fc2Id")
    open var movieMagnetLinks: MutableSet<MovieMagnetLink> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "movie_tags",
        joinColumns = [JoinColumn(name = "fc2_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    open var tags: MutableSet<Tag> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "preview_pictures_movie",
        joinColumns = [JoinColumn(name = "fc2_id")],
        inverseJoinColumns = [JoinColumn(name = "preview_pictures_id")]
    )
    open var previewPictures: MutableSet<PreviewPicture> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "sellers_movie",
        joinColumns = [JoinColumn(name = "fc2_id")],
        inverseJoinColumns = [JoinColumn(name = "seller_id")]
    )
    open var sellers: MutableSet<Seller> = mutableSetOf()
}