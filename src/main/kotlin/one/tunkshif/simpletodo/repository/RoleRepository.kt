package one.tunkshif.simpletodo.repository

import one.tunkshif.simpletodo.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByType(type: Role.Type): Role
}