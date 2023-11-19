package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity(name = "Preview_Picture")
@Table(name = "preview_pictures")
open class PreviewPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("演员唯一标识ID")
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Comment("演员名称")
    @Column(name = "preview_picture", nullable = false)
    open var previewPicture: String? = null

    @ManyToMany(mappedBy = "previewPictures")
    open var movies: MutableSet<Movie> = mutableSetOf()
}