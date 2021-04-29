package one.tunkshif.simpletodo.model

import java.util.*
import javax.persistence.*

@Entity
data class Info(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long,
    @Column(nullable = false, length = 20)
    var title: String,
    @Column(nullable = false, length = 200)
    var content: String,
    @Column(nullable = false)
    var view: Long = 0,
    @OneToOne
    var user: User,
    var createdAt: Date
)
