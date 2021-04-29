package one.tunkshif.simpletodo.repository

import one.tunkshif.simpletodo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(name: String): User?
}