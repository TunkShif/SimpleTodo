package one.tunkshif.simpletodo.model

import java.util.*
import javax.persistence.*

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0,
    @Column(nullable = false, length = 20)
    var title: String,
    @OneToOne
    var user: User,
    var isDone: Boolean = false,
    var createdAt: Date = Date()
)
