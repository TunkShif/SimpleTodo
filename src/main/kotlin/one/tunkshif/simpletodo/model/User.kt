package one.tunkshif.simpletodo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0,
    @Column(nullable = false, length = 20, unique = true)
    private var username: String,
    @Column(nullable = false, length = 100)
    private var password: String,
    @ManyToMany(targetEntity = Role::class)
    private var roles: MutableCollection<Role>
) : UserDetails {

    override fun getUsername() = username

    @JsonIgnore
    override fun getAuthorities() = roles

    @JsonIgnore
    override fun getPassword() = password

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true
}
