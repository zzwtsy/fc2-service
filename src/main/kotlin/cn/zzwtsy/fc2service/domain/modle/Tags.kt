package cn.zzwtsy.fc2service.domain.modle

import cn.zzwtsy.fc2service.dto.TagsDto
import jakarta.persistence.*

@Entity
@Table(name = "tags", schema = "fc2_service")
open class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int = -1

    @Column(name = "tagName", nullable = false)
    open var tagName: String? = null

    @ManyToMany
    @JoinTable(
        name = "movie_tags",
        joinColumns = [JoinColumn(name = "tagId")],
        inverseJoinColumns = [JoinColumn(name = "fc2Id")]
    )
    open var movies: MutableSet<Movies> = mutableSetOf()

    fun toDto(): TagsDto {
        return TagsDto(
            tagName = this.tagName
        )
    }
}