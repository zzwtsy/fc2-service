package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("演员表，存储所有的演员")
@Entity(name = "Preview_Picture")
@Table(name = "preview_pictures", schema = "fc2_service")
open class PreviewPicture {
    @Id
    @Comment("演员唯一标识ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Comment("演员名称")
    @Column(name = "preview_picture", nullable = false)
    open var previewPicture: String? = null

    @ManyToMany(mappedBy = "previewPictures")
    open var movies: MutableSet<Movie> = mutableSetOf()
}