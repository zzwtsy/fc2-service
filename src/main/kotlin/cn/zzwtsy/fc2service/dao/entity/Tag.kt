package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("标签表，存储所有的标签")
@Entity(name = "Tag")
@Table(
    name = "tags", schema = "fc2_service", indexes = [
        Index(name = "tagName", columnList = "tag_name", unique = true)
    ]
)
open class Tag {
    @Id
    @Comment("标签唯一标识ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Comment("标签名")
    @Column(name = "tag_name", nullable = false)
    open var tagName: String? = null

    @ManyToMany(mappedBy = "tags")
    open var movies: MutableSet<Movie> = mutableSetOf()
}