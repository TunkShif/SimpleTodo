package one.tunkshif.simpletodo.config

import one.tunkshif.simpletodo.extension.Logging
import one.tunkshif.simpletodo.model.Role
import one.tunkshif.simpletodo.model.Todo
import one.tunkshif.simpletodo.model.User
import one.tunkshif.simpletodo.repository.RoleRepository
import one.tunkshif.simpletodo.repository.TodoRepository
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
    @Autowired val todoRepository: TodoRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) : ApplicationListener<ContextRefreshedEvent>, Logging {

    var alreadySetUp = false

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (!alreadySetUp) {
            createRoles()
            createUser()
            createTodos()
            alreadySetUp = true
        }
    }

    @Transactional
    fun createUser() {
        userRepository.save(
            User(
                username = "demo",
                password = passwordEncoder.encode("test123"),
                roles = hashSetOf(Role(type = Role.Type.ROLE_USER))
            )
        )
        userRepository.save(
            User(
                username = "admin",
                password = passwordEncoder.encode("test123"),
                roles = hashSetOf(Role(type = Role.Type.ROLE_ADMIN))
            )
        )
    }

    @Transactional
    fun createRoles() {
        roleRepository.save(Role(type = Role.Type.ROLE_USER))
        roleRepository.save(Role(type = Role.Type.ROLE_ADMIN))
    }

    @Transactional
    fun createTodos() {
        val user = userRepository.findByUsername("demo")
        for (i in 1..20) {
            todoRepository.save(
                Todo(
                    title = "this is a todo $i",
                    user = user!!,
                )
            )
        }
    }

}