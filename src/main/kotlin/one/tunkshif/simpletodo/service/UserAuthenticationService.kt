package one.tunkshif.simpletodo.service

import one.tunkshif.simpletodo.model.Role
import one.tunkshif.simpletodo.model.User
import one.tunkshif.simpletodo.repository.RoleRepository
import one.tunkshif.simpletodo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class UserAuthenticationService(
    @PersistenceContext
    val entityManager: EntityManager,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val roleRepository: RoleRepository
) {
    fun register(username: String, password: String): User {
        val userRole = roleRepository.findByType(Role.Type.ROLE_USER)
        return userRepository.save(User(
            username = username,
            password = password,
            roles = mutableListOf(userRole)
        ))
    }

    fun login(username: String, passwd: String) {
        val user = userRepository.findByUsername(username)
        TODO()
    }

    fun logout(user: User) {
        TODO("hhh")
    }

    fun findByToken(token: String): User? {
        TODO()
    }
}