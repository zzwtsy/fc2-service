package cn.zzwtsy.fc2service.domain.modle

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class DirectorMovieId : Serializable {
    @Column(name = "directorId", nullable = false)
    open var directorId: Int? = null

    @Column(name = "fc2Id", nullable = false)
    open var fc2Id: Int? = null
    override fun hashCode(): Int = Objects.hash(directorId, fc2Id)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as DirectorMovieId

        return directorId == other.directorId &&
                fc2Id == other.fc2Id
    }

    companion object {
        private const val serialVersionUID = -2470180793255782616L
    }
}