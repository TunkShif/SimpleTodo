package one.tunkshif.simpletodo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
data class Info(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0,
    @Column(nullable = false, length = 50)
    var title: String,
    @Column(nullable = false, length = 200)
    var content: String,
    @Column(nullable = false)
    var view: Long = 0,
    @OneToOne
    @JsonIgnore
    var user: User,
    var createdAt: Date = Date()
)
