package one.tunkshif.simpletodo.config

import one.tunkshif.simpletodo.extension.Logging
import one.tunkshif.simpletodo.extension.logger
import one.tunkshif.simpletodo.model.Role
import one.tunkshif.simpletodo.model.User
import one.tunkshif.simpletodo.repository.RoleRepository
import one.tunkshif.simpletodo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DataSetupLoader(
    @Autowired val userRepository: UserRepository,
    @Autowired val roleRepository: RoleRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) : ApplicationListener<ContextRefreshedEvent>, Logging {

    var alreadySetUp = false

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (!alreadySetUp) {
            createRoles()
            createUser()
            alreadySetUp = true
        }
    }

    @Transactional
    fun createUser() {
        val userRole = roleRepository.findByType(Role.Type.ROLE_USER)
        val adminRole = roleRepository.findByType(Role.Type.ROLE_ADMIN)

        userRepository.save(
            User(
                username = "demo",
                password = passwordEncoder.encode("test123"),
                roles = mutableListOf(userRole)
            )
        )
        userRepository.save(
            User(
                username = "admin",
                password = passwordEncoder.encode("test123"),
                roles = mutableListOf(adminRole)
            )
        )
    }

    @Transactional
    fun createRoles() {
        roleRepository.save(Role(type = Role.Type.ROLE_USER))
        roleRepository.save(Role(type = Role.Type.ROLE_ADMIN))
    }

}