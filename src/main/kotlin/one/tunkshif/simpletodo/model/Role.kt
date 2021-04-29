package one.tunkshif.simpletodo.model

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0,
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var type: Type
) : GrantedAuthority {
    enum class Type{
        ROLE_USER, ROLE_ADMIN
    }

    override fun getAuthority() = type.toString()
}
