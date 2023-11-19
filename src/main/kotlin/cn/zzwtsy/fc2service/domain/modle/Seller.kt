package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity(name = "Seller")
@Table(name = "sellers")
open class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("导演唯一标识ID")
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Comment("导演名称")
    @Column(name = "name", nullable = false)
    open var name: String? = null

    @ManyToMany(mappedBy = "sellers")
    open var movies: MutableSet<Movie> = mutableSetOf()
}