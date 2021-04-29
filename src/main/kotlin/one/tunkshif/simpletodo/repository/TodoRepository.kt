package one.tunkshif.simpletodo.repository

import one.tunkshif.simpletodo.model.Todo
import one.tunkshif.simpletodo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByUser(user: User): Collection<Todo>
}