package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.TagDto
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity(name = "Tag")
@Table(name = "tags")
open class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("标签唯一标识ID")
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Comment("标签名")
    @Column(name = "tag_name", nullable = false)
    open var tagName: String? = null

    @ManyToMany(mappedBy = "tags")
    open var movies: MutableSet<Movie> = mutableSetOf()

    fun toDto(): TagDto {
        return TagDto(
            this.tagName
        )
    }
}