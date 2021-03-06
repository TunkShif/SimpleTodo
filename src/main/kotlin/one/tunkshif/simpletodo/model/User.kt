package one.tunkshif.simpletodo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    var id: Long = 0,
    @Column(nullable = false, length = 20, unique = true)
    @Size(min=3, max=20, message = "Length ranging from 3 to 20")
    @NotBlank(message = "Name cannot be empty")
    private var username: String,
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Password cannot be empty")
    private var password: String,
    @ManyToMany(targetEntity = Role::class, fetch = FetchType.EAGER)
    private var roles: Set<Role> = hashSetOf()
) : UserDetails {

    override fun getUsername() = username

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
