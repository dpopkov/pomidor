package io.dpopkov.bepomidor.pomidor

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Pomidor(
    var description: String,
    var start: Long,
    var finish: Long,
    @Id
    @GeneratedValue
    var id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pomidor
        if (start != other.start) return false
        if (finish != other.finish) return false
        if (description != other.description) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + finish.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "Pomidor(id=$id, description=$description, start=$start, end=$finish)"
    }
}
