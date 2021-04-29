package one.tunkshif.simpletodo.model

import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
data class Role(
    @Id
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var type: Type
) : GrantedAuthority {
    enum class Type{
        ROLE_USER, ROLE_ADMIN
    }

    @JsonValue
    override fun getAuthority() = type.toString()
}
