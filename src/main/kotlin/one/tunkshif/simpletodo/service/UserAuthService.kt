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
class UserAuthService(
    @PersistenceContext
    private val entityManager: EntityManager,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository
) {
    fun register(username: String, password: String): User {
        return userRepository.save(User(
            username = username,
            password = password,
            roles = hashSetOf(Role(type = Role.Type.ROLE_USER))
        ))
    }
}