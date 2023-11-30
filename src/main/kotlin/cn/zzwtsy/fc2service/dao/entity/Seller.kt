package cn.zzwtsy.fc2service.dao.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("导演表，存储所有的导演")
@Entity(name = "Seller")
@Table(name = "sellers", schema = "fc2_service")
open class Seller {
    @Id
    @Comment("导演唯一标识ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Comment("导演名称")
    @Column(name = "name", nullable = false)
    open var name: String? = null

    @ManyToMany(mappedBy = "sellers")
    open var movies: MutableSet<Movie> = mutableSetOf()
}